package com.example.myapp;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Favourites#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Favourites extends Fragment {
    boolean checkCart = false;
    RecyclerView recyclerView;
    FirebaseRecyclerOptions<Car> options;
    FirebaseRecyclerAdapter<Car,MyViewHolder> adapter;
    DatabaseReference Dataref;
    private DatabaseReference databaseReference;
    private String[] c = new String[0];
    private ArrayList<String> myCartKeyList = new ArrayList<>();
    private int cartLoopCounter = 1;
    private int howManyFound = 0;
    private ArrayList<String> myCartItemsList = new ArrayList<>();
    private ArrayList<String> myCartUrlList = new ArrayList<>();
    private String[] u = new String[0];

    ImageButton cart;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Favourites() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Favourites.
     */
    // TODO: Rename and change types and number of parameters
    public static Favourites newInstance(String param1, String param2) {
        Favourites fragment = new Favourites();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_favourites, container, false);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("cart");
        /* Load items from cart child and store them in array*/
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                String cartKey = snapshot.getKey();
                myCartKeyList.add(cartKey);
                c = myCartKeyList.toArray(new String[0]);
                int limit = c.length;
                Log.d("Limit ", String.valueOf(limit));
                Log.d("loopCounter ", String.valueOf(cartLoopCounter));

                databaseReference.child(c[cartLoopCounter-1]).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists())
                        {
                            String carName=dataSnapshot.child("CarName").getValue().toString();
                            String ImageUrl=dataSnapshot.child("ImageUrl").getValue().toString();
                            myCartItemsList.add(carName);
                            myCartUrlList.add(ImageUrl);
                            u = myCartUrlList.toArray(new String[0]);
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });
                cartLoopCounter++;
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        /*Finish Load items from cart child*/

        Dataref= FirebaseDatabase.getInstance().getReference().child("favourites");
        recyclerView= v.findViewById(R.id.favouritesRecyclerView);
        recyclerView.setHasFixedSize(false);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity().getApplicationContext(),1);
        recyclerView.setLayoutManager(gridLayoutManager);

        cart = v.findViewById(R.id.cartButton);


        LoadData("");
        return v;
    }
    private void LoadData(String data) {
        Query query=Dataref.orderByChild("CarName").startAt(data).endAt(data+"\uf8ff");

        options=new FirebaseRecyclerOptions.Builder<Car>().setQuery(query,Car.class).build();
        adapter=new FirebaseRecyclerAdapter<Car, MyViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final MyViewHolder holder, final int position, @NonNull Car model) {

                final String cartName = model.getCarName();
                final String cartUrl = model.getImageUrl();
                final String ItemsPrice = model.getItemPrice();

                holder.textView.setText(model.getCarName());
                Picasso.get().load(model.getImageUrl()).into(holder.imageView);


                /*Checking if data loaded are present in cart*/
                for (String cartItemsUrl: c)
                {
                    if (cartUrl.equals(cartItemsUrl))
                    {
                        checkCart = true;
                        holder.imageButton.setImageResource(R.drawable.ic_baseline_shopping_redcart_24);
                        holder.imageButton.setBackground(getResources().getDrawable(R.drawable.cart_background_red));
                    }
                }

                holder.imageButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int count = 0;
                        int positionToRemove = -1;

                        if (checkCart)
                        {
                            for (String s:u)
                            {
                                Log.d("key ",s);
                                Log.d("url ",cartUrl);
                                if (cartUrl.equals(s))
                                {
                                    howManyFound++;
                                    positionToRemove = count;
                                }
                                count++;
                            }
                            if (howManyFound > 0)
                            {
                                databaseReference.child(c[positionToRemove]).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Log.d("deleted ","Successful");
                                        checkCart = false;
                                        holder.imageButton.setImageResource(R.drawable.ic_baseline_shopping_cart_24);
                                        holder.imageButton.setBackground(getResources().getDrawable(R.drawable.cart_background));
                                    }
                                });
                            }
                            else
                            {
                                final  String key=databaseReference.push().getKey();
                                HashMap hashMap=new HashMap();
                                hashMap.put("CarName",cartName);
                                hashMap.put("ImageUrl",cartUrl);
                                hashMap.put("ItemPrice",ItemsPrice);
                                databaseReference.child(key).setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        checkCart = true;
                                        holder.imageButton.setImageResource(R.drawable.ic_baseline_shopping_redcart_24);
                                        holder.imageButton.setBackground(getResources().getDrawable(R.drawable.cart_background_red));
                                        //Toast.makeText(MainActivity.this, "Data Successfully Uploaded!", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        }
                        else {
                            Log.d("2else"," executed");
                            final  String key=databaseReference.push().getKey();
                            HashMap hashMap=new HashMap();
                            hashMap.put("CarName",cartName);
                            hashMap.put("ImageUrl",cartUrl);
                            hashMap.put("ItemPrice",ItemsPrice);
                            databaseReference.child(key).setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    checkCart = true;
                                    holder.imageButton.setImageResource(R.drawable.ic_baseline_shopping_redcart_24);
                                    holder.imageButton.setBackground(getResources().getDrawable(R.drawable.cart_background_red));
                                    //Toast.makeText(MainActivity.this, "Data Successfully Uploaded!", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                });



//                holder.v.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Intent intent=new Intent(getActivity(),ViewActivity.class);
//                        intent.putExtra("CarKey",getRef(position).getKey());
//                        intent.putExtra("category","earphones");
//                        startActivity(intent);
//                    }
//                });

            }
            @NonNull
            @Override
            public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.singe_view_favorites,parent,false);
                return new MyViewHolder(v);
            }
        };
        adapter.startListening();
        recyclerView.setAdapter(adapter);

    }
}
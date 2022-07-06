package com.example.myapp;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
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
 * Use the {@link OnCart#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OnCart extends Fragment {
    RecyclerView recyclerView;
    FirebaseRecyclerOptions<Car> options;
    FirebaseRecyclerAdapter<Car,ViewOncart> adapter;
    private String[] c = new String[0];
    Button buyAll;

    DatabaseReference Dataref,Purchases;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ArrayList<String> myCartKeyList = new ArrayList<>();
    private int cartLoopCounter = 1;
    private ArrayList<String> myCartItemsList = new ArrayList<>();
    private ArrayList<String> myCartUrlList = new ArrayList<>();
    private ArrayList<String> itemsList = new ArrayList<>();
    private ArrayList<Integer> itemsIdList = new ArrayList<>();
    private ArrayList<String> imageUrls = new ArrayList<>();
    private String[] u = new String[0];

    public OnCart() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment OnCart.
     */
    // TODO: Rename and change types and number of parameters
    public static OnCart newInstance(String param1, String param2) {
        OnCart fragment = new OnCart();
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
        View v =  inflater.inflate(R.layout.fragment_on_cart, container, false);
        buyAll = v.findViewById(R.id.buyAll);
        Dataref= FirebaseDatabase.getInstance().getReference().child("cart");
        Purchases= FirebaseDatabase.getInstance().getReference().child("purchases");
        recyclerView= v.findViewById(R.id.OnCartRecyclerView);
        recyclerView.setHasFixedSize(false);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity().getApplicationContext(),1);
        recyclerView.setLayoutManager(gridLayoutManager);

        buyAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        /* Load items from cart child and store them in array*/
        Dataref.addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                String cartKey = snapshot.getKey();
                myCartKeyList.add(cartKey);
                c = myCartKeyList.toArray(new String[0]);
//                int limit = c.length;
//                Log.d("Limit ", String.valueOf(limit));
//                Log.d("loopCounter ", String.valueOf(cartLoopCounter));

                Dataref.child(c[cartLoopCounter-1]).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists())
                        {
                            try {
                                String carName=dataSnapshot.child("CarName").getValue().toString();
                                String ImageUrl=dataSnapshot.child("ImageUrl").getValue().toString();
                                myCartItemsList.add(carName);
                                myCartUrlList.add(ImageUrl);
                                u = myCartUrlList.toArray(new String[0]);
                            }catch (Exception e){

                            }
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

        LoadData("");
        return v;
    }
    private void LoadData(String data) {
        Query query=Dataref.orderByChild("CarName").startAt(data).endAt(data+"\uf8ff");

        options=new FirebaseRecyclerOptions.Builder<Car>().setQuery(query,Car.class).build();
        adapter=new FirebaseRecyclerAdapter<Car, ViewOncart>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final ViewOncart holder, final int position, @NonNull final Car model) {

                final int[] quantity = new int[1];
                final int[] total_single_item = new int[1];
                final int[] total_amount = new int[1];

                itemsList.add(model.getCarName()+"\n");
                itemsIdList.add(model.getItemId());
                imageUrls.add(model.getImageUrl());
                Log.d("ids ==",String.valueOf(model.getItemId()));

                holder.textView.setText(model.getCarName());
                total_single_item[0] = Integer.parseInt(model.getItemPrice());
                holder.totalPrice.setText("Tsh "+ total_single_item[0] +"/=");
//                holder.delete.setId(View.generateViewId());
                Picasso.get().load(model.getImageUrl()).into(holder.imageView);

                final String url = model.getImageUrl();


                holder.delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int count = 0;
                        for (String s:c)
                        {
                            Log.d("key ",s);
                            if (url.equals(u[count]))
                            {
                                Dataref.child(c[count]).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Log.d("deleted ","Successful");
                                    }
                                });
                            }
                            count++;
                        }
                    }
                });
                holder.buyNow.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Timestamp timeStamp = Timestamp.now();
                        String uid = FirebaseAuth.getInstance().getUid().toString();
                        ArrayList<String> item1 = new ArrayList<>();
                        item1.add(model.getCarName());
//                        String amount = model.getItemPrice();
                        String amount = String.valueOf(total_single_item[0]);
                        ArrayList<Integer> productId = new ArrayList<>();
                        productId.add(model.getItemId());
//                        String productId = String.valueOf(model.getItemId());
                        ArrayList<String> singleImageUrl = new ArrayList<>();
                        singleImageUrl.add(model.getImageUrl());


                        HashMap hashMap = new HashMap();
                        hashMap.put("itemsId", productId);
                        hashMap.put("imageUrls", singleImageUrl);
                        hashMap.put("amount", amount);
                        hashMap.put("quantity", quantity[0]);
                        hashMap.put("items",item1);
                        hashMap.put("uid",uid);
                        hashMap.put("time", timeStamp.toDate().toString());

                        Purchases.child(timeStamp.toDate().toString()).setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(getContext(), "Item bought", Toast.LENGTH_SHORT).show();
                                int count = 0;
                                for (String s:c)
                                {
                                    Log.d("key ",s);
                                    if (url.equals(u[count]))
                                    {
                                        Dataref.child(c[count]).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Log.d("deleted ","Successful");
                                            }
                                        });
                                    }
                                    count++;
                                }
                            }
                        });
                    }
                });
                holder.quantity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        quantity[0] = Integer.parseInt((String) parent.getItemAtPosition(position));
                        total_single_item[0] = quantity[0] *Integer.parseInt(model.getItemPrice());
                        holder.totalPrice.setText("Tsh "+ total_single_item[0] +"/=");
//                        itemsList.add(model.getCarName()+quantity[0]+"\n");
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });
                total_amount[0] += total_single_item[0];

                buyAll.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Timestamp timeStamp = Timestamp.now();
                        String uid = FirebaseAuth.getInstance().getUid().toString();
                        String item1 = model.getCarName();
//                        String amount = model.getItemPrice();
                        String amount = String.valueOf(total_amount[0]);
                        String productId = String.valueOf(model.getItemId());

                        HashMap hashMap = new HashMap();
                        hashMap.put("itemsId", itemsIdList);
                        hashMap.put("imageUrls", imageUrls);
                        hashMap.put("amount", amount);
                        hashMap.put("quantity", quantity[0]);
                        hashMap.put("items",itemsList);
                        hashMap.put("uid",uid);
                        hashMap.put("time", timeStamp.toDate().toString());

                        Purchases.child(timeStamp.toDate().toString()).setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(getContext(), "Item bought", Toast.LENGTH_SHORT).show();


                                Dataref.removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Log.d("deleted ","Successful");
                                    }
                                });

//                                int count = 0;
//                                for (String s:c)
//                                {
//                                    Log.d("key ",s);
//                                    if (url.equals(u[count]))
//                                    {
//                                        Dataref.child(c[count]).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
//                                            @Override
//                                            public void onSuccess(Void aVoid) {
//                                                Log.d("deleted ","Successful");
//                                            }
//                                        });
//                                    }
//                                    count++;
//                                }
                                itemsIdList.clear();
                                imageUrls.clear();
                                itemsList.clear();
                            }
                        });
                    }
                });

            }
            @NonNull
            @Override
            public ViewOncart onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.single_cart_item,parent,false);
                return new ViewOncart(v);
            }
        };
        adapter.startListening();
        recyclerView.setAdapter(adapter);

    }
}
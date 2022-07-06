 package com.example.myapp;

 import android.Manifest;
 import android.content.Intent;
 import android.content.pm.PackageManager;
 import android.net.Uri;
 import android.os.Build;
 import android.os.Bundle;
 import android.text.Editable;
 import android.text.TextWatcher;
 import android.util.Log;
 import android.view.LayoutInflater;
 import android.view.MenuItem;
 import android.view.View;
 import android.view.ViewGroup;
 import android.widget.EditText;
 import android.widget.ImageView;
 import android.widget.TextView;
 import android.widget.Toast;

 import androidx.annotation.NonNull;
 import androidx.appcompat.app.AppCompatActivity;
 import androidx.core.app.ActivityCompat;
 import androidx.recyclerview.widget.GridLayoutManager;
 import androidx.recyclerview.widget.RecyclerView;

 import com.denzcoskun.imageslider.ImageSlider;
 import com.denzcoskun.imageslider.constants.ScaleTypes;
 import com.denzcoskun.imageslider.interfaces.ItemClickListener;
 import com.denzcoskun.imageslider.models.SlideModel;
 import com.firebase.ui.database.FirebaseRecyclerAdapter;
 import com.firebase.ui.database.FirebaseRecyclerOptions;
 import com.google.android.material.bottomnavigation.BottomNavigationView;
 import com.google.firebase.database.DataSnapshot;
 import com.google.firebase.database.DatabaseError;
 import com.google.firebase.database.DatabaseReference;
 import com.google.firebase.database.FirebaseDatabase;
 import com.google.firebase.database.Query;
 import com.google.firebase.database.ValueEventListener;
 import com.squareup.picasso.Picasso;

 import java.util.ArrayList;
 import java.util.List;
 import java.util.Objects;

 public class HomeActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{

    private static final String TAG = "HomeActivity";

    EditText inputSearch;
    RecyclerView recyclerView;
//    FloatingActionButton floatingbtn;
    FirebaseRecyclerOptions<Car> options;
    FirebaseRecyclerAdapter<Car,MyHomeViewHolder>adapter;
    DatabaseReference Dataref;
    ImageSlider imageSlider;
    final List<SlideModel> slideModelList = new ArrayList<>();
    BottomNavigationView bottomNavigationView;

    Settings settingsFragment = new Settings();
    Favourites favouritesFragment = new Favourites();
    MyHome myHomeFragment = new MyHome();
    Chats chatsFragment = new Chats();
    OnCart onCartFragment = new OnCart();

    String email;
    Uri photo;
    String photoUrl;
    TextView currentUserName;
    String userName;
    ImageView profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //ask permissions
        isReadStoragePermissionGranted();
        isWriteStoragePermissionGranted();
        
//        currentUserName = findViewById(R.id.currentUserName);
//        profile = findViewById(R.id.imageView3);
        userName = getIntent().getStringExtra("userName");
        email = getIntent().getStringExtra("email");
        photoUrl = getIntent().getStringExtra("photo");
//        photo = Uri.parse(photoUrl) ;
        if (userName != null)
        {
            Log.d("Welcome ","Mr. "+SharebleResources.contactName);
//            currentUserName.setText(userName);
//            SharebleResources.imageUrl = photoUrl;
//            Picasso.get().load(photoUrl).into(profile);
        }

        bottomNavigationView = findViewById(R.id.home_bottom_navigation_view);
        bottomNavigationView.setSelectedItemId(R.id.startHome);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);

        Dataref= FirebaseDatabase.getInstance().getReference().child("home");
        inputSearch=findViewById(R.id.inputSearch);
        recyclerView=findViewById(R.id.recyclerView);
//        floatingbtn=findViewById(R.id.floatingbtn);
        recyclerView.setHasFixedSize(false);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(),1);
        recyclerView.setLayoutManager(gridLayoutManager);

        imageSlider = findViewById(R.id.image_slider);
        FirebaseDatabase.getInstance().getReference().child("slide").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot:snapshot.getChildren())
                {
                    slideModelList.add(new SlideModel(dataSnapshot.child("ImageUrl").getValue().toString(),dataSnapshot.child("CarName").getValue().toString(), ScaleTypes.FIT));
                }
                imageSlider.setImageList(slideModelList,ScaleTypes.CENTER_CROP);

                imageSlider.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onItemSelected(int i) {
                        Log.d("HomeActivity","imeguswa");
                        Toast.makeText(HomeActivity.this,slideModelList.get(i).getTitle().toString(),Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

//        floatingbtn.setOnClickListener(new View.OnClickListener() {
//            @RequiresApi(api = Build.VERSION_CODES.N)
//            @Override
//            public void onClick(View v) {
//                SimpleDateFormat formatter = new SimpleDateFormat("yyyy_MM_dd");
//                Date now = new Date();
//                String fileName = formatter.format(now) + ".dat";//like 2016_01_12.txt
//
//                try
//                {
//                    File root = new File(Environment.getExternalStorageDirectory()+File.separator+"MaishaSupermarket", "Dataset");
//                    //File root = new File(Environment.getExternalStorageDirectory(), "Notes");
//                    if (!root.exists()) {
//                        if(root.mkdirs()){
//                            // do something
//                            File gpxfile = new File(root, fileName);
//
//
//                            FileWriter writer = new FileWriter(gpxfile,true);
//                            writer.append("1 2 3 4 5"+"\n6 7 8 9 10\n11 12 13 14 15");
//                            writer.flush();
//                            writer.close();
//                            Toast.makeText(HomeActivity.this, "Data has been written to Dataset", Toast.LENGTH_SHORT).show();
//                        }else{
//                            Toast.makeText(HomeActivity.this, "Failed to mkdir 02", Toast.LENGTH_SHORT).show();
//                        }
//                    }else{
//
//                        File gpxfile = new File(root, fileName);
//
//
//                        FileWriter writer = new FileWriter(gpxfile,true);
//                        writer.append("1 2 3 4 5"+"\n6 7 8 9 10\n11 12 13 14 15");
//                        writer.flush();
//                        writer.close();
//                        Toast.makeText(HomeActivity.this, "Data has been written to Dataset", Toast.LENGTH_SHORT).show();
//                    }
////                    if (!root.exists())
////                    {
////                        root.mkdirs();
////                    }
//                }
//                catch(IOException e)
//                {
//                    e.printStackTrace();
//                    Toast.makeText(HomeActivity.this, "Failed to write to Dataset", Toast.LENGTH_SHORT).show();
//                }
//                startActivity(new Intent(getApplicationContext(),MainActivity.class));
//            }
//        });

        LoadData("");

        inputSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString()!=null)
                {
                    LoadData(s.toString());
                }
                else
                {
                    LoadData("");
                }
            }
        });
        inputSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputSearch.setCursorVisible(true);
            }
        });
    }

    private void LoadData(String data) {
        Query query=Dataref.orderByChild("CarName").startAt(data).endAt(data+"\uf8ff");

        options=new FirebaseRecyclerOptions.Builder<Car>().setQuery(query,Car.class).build();
        adapter=new FirebaseRecyclerAdapter<Car, MyHomeViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull MyHomeViewHolder holder, final int position, @NonNull Car model) {
                if (model.getCarName().equals("Sports")){
                    holder.separation_space.setVisibility(View.VISIBLE);
                }else {
                    holder.separation_space.setVisibility(View.GONE);
                }
                holder.textView.setText(model.getCarName());
                Picasso.get().load(model.getImageUrl()).into(holder.imageView);
                try {
                    Picasso.get().load(model.getImageUrl1()).into(holder.imageView_one);
                    try{
                        Picasso.get().load(model.getImageUrl2()).into(holder.imageView_two);
                    }catch (Exception e){
                    }
                    try{
                        Picasso.get().load(model.getImageUrl3()).into(holder.imageView_three);
                    }catch (Exception e){
                    }
                }catch (Exception e){
//                    holder.imageView_one.setImageResource(R.drawable.ic_baseline_favorite_like_24);
                    Picasso.get().load(R.drawable.ic_baseline_favorite_like_24).into(holder.imageView_one);
                    holder.imageView_two.setVisibility(View.GONE);
                    holder.imageView_three.setVisibility(View.GONE);
                    holder.grid.setColumnCount(1);
                    holder.imageView.setAdjustViewBounds(true);
//                    holder.imageView.setMinimumWidth(350);
                }

                holder.v.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Dataref.child(Objects.requireNonNull(getRef(position).getKey())).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if (dataSnapshot.exists())
                                {
                                    String carName=dataSnapshot.child("CarName").getValue().toString();
//                                    if (carName.equals("Beauty"))
//                                    {
//                                        Intent intent = new Intent(HomeActivity.this,Beauty.class);
//                                        startActivity(intent);
//                                    }
//                                    else if (carName.equals("Electronics"))
//                                    {
//                                        Intent intent = new Intent(HomeActivity.this,Electronics.class);
//                                        startActivity(intent);
//                                    }
                                    if (carName.equals("Drinks"))
                                    {
                                        Intent intent = new Intent(HomeActivity.this, Drinks.class);
                                        startActivity(intent);
                                    }
                                    else if (carName.equals("Food"))
                                    {
                                        Intent intent = new Intent(HomeActivity.this,Food.class);
                                        startActivity(intent);
                                    }
                                    else if (carName.equals("Home"))
                                    {
                                        Intent intent = new Intent(HomeActivity.this,Home.class);
                                        startActivity(intent);
                                    }
                                    else if (carName.equals("Snacks & Bites"))
                                    {
                                        Intent intent = new Intent(HomeActivity.this, Snacks.class);
                                        startActivity(intent);
                                    }
                                    else if (carName.equals("Sports"))
                                    {
                                        Intent intent = new Intent(HomeActivity.this,Sports.class);
                                        startActivity(intent);
                                    }
//                                    else if (carName.equals("Women"))
//                                    {
//                                        Intent intent = new Intent(HomeActivity.this,Women.class);
//                                        startActivity(intent);
//                                    }
//                                    String ImageUrl=dataSnapshot.child("ImageUrl").getValue().toString();
//                                    Picasso.get().load(ImageUrl).into(imageView);
//                                    textView.setText(carName);
                                    else {
                                        Intent intent=new Intent(HomeActivity.this,ViewActivity.class);
                                        intent.putExtra("CarKey",getRef(position).getKey());
                                        intent.putExtra("category","home");
                                        startActivity(intent);
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }
                });

            }

            @NonNull
            @Override
            public MyHomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.single_home_view,parent,false);
                return new MyHomeViewHolder(v);
            }
        };
        adapter.startListening();
        recyclerView.setAdapter(adapter);

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId())
        {
            case R.id.settings:
                getSupportFragmentManager().beginTransaction().replace(R.id.homeContainer,settingsFragment).commit();
                return true;
            case R.id.favourites:
                getSupportFragmentManager().beginTransaction().replace(R.id.homeContainer,favouritesFragment).commit();
                return true;
            case R.id.startHome:
                getSupportFragmentManager().beginTransaction().replace(R.id.homeContainer,myHomeFragment).commit();
                return true;
//            case R.id.chats:
//                getSupportFragmentManager().beginTransaction().replace(R.id.homeContainer,chatsFragment).commit();
//                return true;
            case R.id.cart:
                getSupportFragmentManager().beginTransaction().replace(R.id.homeContainer,onCartFragment).commit();
                return true;
        }
        return false;
    }

     public  boolean isReadStoragePermissionGranted() {
         if (Build.VERSION.SDK_INT >= 23) {
             if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                     == PackageManager.PERMISSION_GRANTED) {
                 Log.v(TAG,"Permission is granted1");
                 return true;
             } else {

                 Log.v(TAG,"Permission is revoked1");
                 ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 3);
                 return false;
             }
         }
         else { //permission is automatically granted on sdk<23 upon installation
             Log.v(TAG,"Permission is granted1");
             return true;
         }
     }

     public  boolean isWriteStoragePermissionGranted() {
         if (Build.VERSION.SDK_INT >= 23) {
             if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                     == PackageManager.PERMISSION_GRANTED) {
                 Log.v(TAG,"Permission is granted2");
                 return true;
             } else {

                 Log.v(TAG,"Permission is revoked2");
                 ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 2);
                 return false;
             }
         }
         else { //permission is automatically granted on sdk<23 upon installation
             Log.v(TAG,"Permission is granted2");
             return true;
         }
     }

     @Override
     public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
         super.onRequestPermissionsResult(requestCode, permissions, grantResults);
         switch (requestCode) {
             case 2://write
                 Log.d(TAG, "External storage2");
                 if(grantResults[0]== PackageManager.PERMISSION_GRANTED){
                     Log.v(TAG,"Permission: "+permissions[0]+ "was "+grantResults[0]);
                     Toast.makeText(HomeActivity.this,"Permission Granted!",Toast.LENGTH_LONG).show();
                     //resume tasks needing this permission
//                     downloadPdfFile();
                 }else{
                     Toast.makeText(HomeActivity.this,"Permission Denied!",Toast.LENGTH_LONG).show();
//                     progress.dismiss();
                 }
                 break;

             case 3://read
                 Log.d(TAG, "External storage1");
                 if(grantResults[0]== PackageManager.PERMISSION_GRANTED){
                     Log.v(TAG,"Permission: "+permissions[0]+ "was "+grantResults[0]);
                     Toast.makeText(HomeActivity.this,"Permission Granted!",Toast.LENGTH_LONG).show();
                     //resume tasks needing this permission
//                     SharePdfFile();
                 }else{
                     Toast.makeText(HomeActivity.this,"Permission Denied!",Toast.LENGTH_LONG).show();
//                     progress.dismiss();
                 }
                 break;
         }
     }
}

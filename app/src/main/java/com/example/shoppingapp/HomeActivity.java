package com.example.shoppingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    BottomNavigationView bnv;
    RecyclerView rv;
    public static final String PRODUCT_KEY = "product_key";
    private static final int PERMISSION_REQ_COD = 1;
    public static final String TABLE_NAME_KEY = "table_key";
    SharedPreferences shp;
    SharedPreferences.Editor shpEditor;
    SharedPreferences shp_id;
    SharedPreferences.Editor shpEditor_id;
    public static boolean flag;
    ShoppingDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        flag = false;
        db = new ShoppingDatabase(this);

        //insertAllData();

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},PERMISSION_REQ_COD);
        }

        rv = findViewById(R.id.rv_home);
        shp = getSharedPreferences("myPreferences", MODE_PRIVATE);
        shp_id = getSharedPreferences("Preferences_id", MODE_PRIVATE);

        ArrayList<Products> products1 = new ArrayList<>();
        products1 = db.getAllProducts(ShoppingDatabase.TB_PRODUCT_DISCOUNT);
        HomeAdabter adapter = new HomeAdabter(products1, new OnRecyclerViewClickListener() {
            @Override
            public void OnItemClick(int productId) {
                Intent i = new Intent(getBaseContext(),DisplayProductsActivity.class);
                i.putExtra(PRODUCT_KEY,productId);
                i.putExtra(TABLE_NAME_KEY,ShoppingDatabase.TB_PRODUCT_DISCOUNT);
                flag = true;
                startActivity(i);
            }
        });
        RecyclerView.LayoutManager lm = new LinearLayoutManager(this,RecyclerView.HORIZONTAL,false);
        rv.setLayoutManager(lm);
        rv.setHasFixedSize(true);
        rv.setAdapter(adapter);

        bnv = findViewById(R.id.BottomNavigationView);
        bnv.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){
                    case R.id.home:
                        Intent intent1 = new Intent(getBaseContext(),HomeActivity.class);
                        startActivity(intent1);
                        break;
                    case R.id.products:
                        Intent intent2 = new Intent(getBaseContext(),MainActivity.class);
                        startActivity(intent2);
                        break;
                    case R.id.profile:
                        Intent intent3 = new Intent(getBaseContext(),ProfileActivity.class);
                        startActivity(intent3);
                        break;
                    case R.id.basket:
                        Intent intent4 = new Intent(getBaseContext(),PurchasesActivity.class);
                        startActivity(intent4);
                        break;
                }

                return true;
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.nav_up_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.logout:
                logout();
                break;
            case R.id.settings:
                Intent intent = new Intent(this,SettingActivity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void logout() {
        try {
            if (shp == null){
                shp = getSharedPreferences("myPreferences", MODE_PRIVATE);
            }
            shpEditor = shp.edit();
            shpEditor.putInt("user", 0);
            shpEditor.commit();

            if (shp_id == null){
                shp_id = getSharedPreferences("Preferences_id", MODE_PRIVATE);
            }
            shpEditor_id = shp_id.edit();
            shpEditor_id.putInt("user_id",0);
            shpEditor_id.commit();

            startActivity(new Intent(HomeActivity.this,LoginActivity.class));
            finish();

        }catch (Exception ex){
            Toast.makeText(this, ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case PERMISSION_REQ_COD:
                if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    //تم الحصول عليه
                }else{

                }
        }
    }

    public void insertAllData(){
        // game
        Products p = new Products(R.drawable.playstation4,"playstation4",2500,"sony",25,"computer noc Barnes & Noble Retail and Samsung Electronics launched the new Samsung Galaxy Tab 4 Noc tablet in New York.",10,(float) 3.5);
        db.insertProduct(p,ShoppingDatabase.TB_GAME);

        Products p1 = new Products(R.drawable.playstation5,"playstation5&4",7600,"sony",81,"Sony PlayStation 5 Console + 2 DualSense Wireless Controller+ fifa 21 standard edition All prices include VAT..",25,(float) 3.5);
        db.insertProduct(p1,ShoppingDatabase.TB_GAME);

        Products p2 = new Products(R.drawable.pes2012,"pes2012",4900,"Konami",45,"AUTHENTIC LEAGUES - Fully licensed leagues are coming to PES 2012. Details to be revealed soon!",0,(float) 3.5);
        db.insertProduct(p2,ShoppingDatabase.TB_GAME);

        Products p3 = new Products(R.drawable.menacing,"menacing",5850,"acer",32,"Acer Predator Thronos Air, Gaming Throne with Massage Pad and Gaming Chair, Up to 3 Displays, 130 Degrees Recline, LED Lighting, PC Landing Pad, Stabilizing Arm (PC and Monitors Sold Separately)",0,(float) 3.5);
        db.insertProduct(p3,ShoppingDatabase.TB_GAME);

        Products p4 = new Products(R.drawable.steelchair3,"char gaming",6000,"acer",51,"different colors gaming Thermaltake U-Fit Black-Red Gaming Chair GGC-UFT-BRMWDS-01 GGC-UFT-BRMWDS-01",0,(float) 3.5);
        db.insertProduct(p4,ShoppingDatabase.TB_GAME);

        Products p5 = new Products(R.drawable.gamecontroller5,"gamecontroller5",800," Powerextra",26,"Powerextra Xbox 360 Controllers,USB Gamepad Wired Controller Improved Ergonomic Joystick Dual Vibration,Compatible with Xbox 360 Slim/Xbox 360/PC(Windows / 7/8.1/10),Black",0,(float) 3.5);
        db.insertProduct(p5,ShoppingDatabase.TB_GAME);

         // electrics
        Products p11 = new Products(R.drawable.microwave,"Microwave",270,"TOSHIBA",92,"Microwave is an electric device used to heat various types of foods, and this type of oven is different from traditional ovens",0,(float) 3.5);
        db.insertProduct(p11,ShoppingDatabase.TB_ELECTRICS);

        Products p12 = new Products(R.drawable.vacuumcleaner,"vacuum cleaner",210,"TOSHIBA",32,"Hoover Vacuum Cleaner - 2000W, Black - Red TCP2010020 Buy with installments and pay EGP 51.06 for 48 months with select banks.",10,(float) 3.5);
        db.insertProduct(p12,ShoppingDatabase.TB_ELECTRICS);

        Products p13 = new Products(R.drawable.washer,"Washer",690,"TOSHIBA",81,"TOSHIBA Washing Machine Half Automatic Capacity : 12 Kg Max Spin Speed : 1400 RPM With 2 Motors Washing Machine Giant Super Size Works With All Kinds of Regular Powders Vortexes System",0,(float) 3.5);
        db.insertProduct(p13,ShoppingDatabase.TB_ELECTRICS);

        Products p14 = new Products(R.drawable.electricracket,"electric racket",270,"TOSHIBA",92,"KENWOOD HAND MIXER HM330 Buy with installments and pay EGP 16.97 for 48 months with select banks.",0,(float) 3.5);
        db.insertProduct(p14,ShoppingDatabase.TB_ELECTRICS);

        Products p15 = new Products(R.drawable.iron,"iron",210,"TOSHIBA",32,"Steam iron A small household appliance A smooth piece with a handle, heated by a heat source to a temperature suitable for the garment.",10,(float) 3.5);
        db.insertProduct(p15,ShoppingDatabase.TB_ELECTRICS);

        Products p16 = new Products(R.drawable.kettle,"Kettle",190,"HOHO",81,"Stay focused on your everyday goals with a stimulating hot cup of tea prepared using the Hoho 1.5L Electric Kettle.",0,(float) 3.5);
        db.insertProduct(p16,ShoppingDatabase.TB_ELECTRICS);

        //fashion

        Products p21 = new Products(R.drawable.teshert,"t_shirt",45,"Zara",52,"Black t_shirt size XL.",0,(float) 3.5);
        db.insertProduct(p21,ShoppingDatabase.TB_FASHION);

        Products p22 = new Products(R.drawable.pajamas,"Pajama",88,"Zara",52,"Light pink longitudinal pajamas, two pieces, suitable for all ages and sizes, and are available with limited discounts.",0,(float) 3.5);
        db.insertProduct(p22,ShoppingDatabase.TB_FASHION);

        Products p23 = new Products(R.drawable.belt1,"Belt",88,"Zara",44,"Brown genuine leather belt with a silver tong, elegant appearance and suitable with all pants Available at a discount for a limited time.",15,(float) 3.5);
        db.insertProduct(p23,ShoppingDatabase.TB_FASHION);

        Products p24 = new Products(R.drawable.shoes1,"shoes",121,"Zara",150,"Casual Shoe Lace Up Shoes Faux Leather a108 - 42",0,(float) 3.5);
        db.insertProduct(p24,ShoppingDatabase.TB_FASHION);

        Products p25 = new Products(R.drawable.sandalsandbag,"sandal sandbag",180,"Zara",44,"Christian Louboutin Women's Blue Shiny Patent Leather Kate Pumps Size 37.",0,(float) 3.5);
        db.insertProduct(p25,ShoppingDatabase.TB_FASHION);

        Products p26 = new Products(R.drawable.watch2,"watch",150,"Zara",44,"Stainless steel men formal wristwatch blak color and Model Number : for51.",10,(float) 3.5);
        db.insertProduct(p26,ShoppingDatabase.TB_FASHION);

        Products p27 = new Products(R.drawable.suit,"suit",1200,"Dior",49,"Black suits have a very diverse designs and their adoption at different times of the day.",0,(float) 3.5);
        db.insertProduct(p27,ShoppingDatabase.TB_FASHION);

        Products p28 = new Products(R.drawable.glasses1,"glasses",80,"Dior",61,"Frame Material : TR-90 ,Polarized Lenses : No Lens, Width : 46 millimeters Lens. ",0,(float) 3.5);
        db.insertProduct(p28,ShoppingDatabase.TB_FASHION);

         // home
        Products p31 = new Products(R.drawable.table,"table",130,"Wood",52,"About this item Condition Open box, Quantity 1 available, Item,Number 195079547477, Shape Rectangular, Colour Brown.",0,(float) 3.5);
        db.insertProduct(p31,ShoppingDatabase.TB_HOME_COOKER);

        Products p32 = new Products(R.drawable.bedframe,"Bed",250,"Zara",50,"A piece of furniture used for sleeping or resting. Beds have ranged throughout history from simple straw mattresses to pieces of precious furniture in luxurious frames decorated with precious fabrics.",0,(float) 3.5);
        db.insertProduct(p32,ShoppingDatabase.TB_HOME_COOKER);

        Products p33 = new Products(R.drawable.armoires,"wardrobe",188,"perfect",44,"Get the best solution for storing your clothes with a variety of single and small wardrobes in different styles Beech wood wardrobe.",15,(float) 3.5);
        db.insertProduct(p33,ShoppingDatabase.TB_HOME_COOKER);

        Products p34 = new Products(R.drawable.cookwarefrying,"cooking solution",85,"Zara",15,"A healthy food bowl that is not affected by heat, easy to clean, and there is a complete set of the rest of the set",0,(float) 3.5);
        db.insertProduct(p34,ShoppingDatabase.TB_HOME_COOKER);

        Products p35 = new Products(R.drawable.tuftedwhite,"coush",180,"generic",44,"Rattan Swing Hammock Hanging Chair, Comfortable and safe air chair available for a limited time.",0,(float) 3.5);
        db.insertProduct(p35,ShoppingDatabase.TB_HOME_COOKER);

        Products p36 = new Products(R.drawable.kitchenutensil,"distribution kit",150,"Zara",47,"IKEA Fulländad 5-Piece Kitchen Utensil Set, Gray and Gentle to pots and pans with non-stick coating. The Package length of the product is 13.75 inches.",10,(float) 3.5);
        db.insertProduct(p36,ShoppingDatabase.TB_HOME_COOKER);

        Products p37 = new Products(R.drawable.teastrainer,"tea strainer",1200,"Dior",49, "Thermal Porcelain Tea Set - Tree, Black and This product will be an excellent pick for you , Designed to perfection, Made with an excellent craftsmanship.",0,(float) 3.5);
        db.insertProduct(p37,ShoppingDatabase.TB_HOME_COOKER);

        Products p38 = new Products(R.drawable.fryingpan,"goblet",80,"Dior",61,"Turkish granite crepe pan - size 30 cm, safe and healthy, not affected by heat and soft, easy to clean. ",0,(float) 3.5);
        db.insertProduct(p38,ShoppingDatabase.TB_HOME_COOKER);

         // book

        Products p41 = new Products(R.drawable.java,"java",130,"E_Book",52,"A large and distinguished book that is a comprehensive Arabic reference for the Java language that contains building the reader's self-confidence and forming a positive source of strengthening for him.",0,(float) 3.5);
        db.insertProduct(p41,ShoppingDatabase.TB_BOOK);

        Products p42 = new Products(R.drawable.c,"C++",250,"E_Book",50,"As an alternative, the Kindle eBook is available now and can be read on any device with the free Kindle app.",0,(float) 3.5);
        db.insertProduct(p42,ShoppingDatabase.TB_BOOK);

        Products p43 = new Products(R.drawable.android,"Android",388,"E_Book",44,"Android course book is a free and open source operating system based on the Linux kernel designed primarily for touch screen devices such as smartphones and tablets.",15,(float) 3.5);
        db.insertProduct(p43,ShoppingDatabase.TB_BOOK);

        Products p44 = new Products(R.drawable.kotlin,"Kotlin",285,"E_Book",15,"Programming in the Kotlin language, which is a very useful book for those wishing to professionally learn Kotlin, a language developed from Java and used to make applications.",0,(float) 3.5);
        db.insertProduct(p44,ShoppingDatabase.TB_BOOK);

        Products p45 = new Products(R.drawable.net,".net",180,"E_Book",44,"is a proprietary software framework developed by Microsoft that runs primarily on Microsoft Windows.",0,(float) 3.5);
        db.insertProduct(p45,ShoppingDatabase.TB_BOOK);

        Products p46 = new Products(R.drawable.prolog,"prolog",150,"E_Book",47,"The logic programming language PROLOG (Programmation en Logique) was conceived by Alain Colmerauer at the University of Aix-Marseille, France, where the language was first implemented in 1973.",10,(float) 3.5);
        db.insertProduct(p46,ShoppingDatabase.TB_BOOK);

        Products p47 = new Products(R.drawable.javascript,"Java Script",200,"E_Book",49, "Js is a high-level programming language used primarily in web browsers to create more interactive pages. It is currently being developed by Mozilla Corporation.",0,(float) 3.5);
        db.insertProduct(p47,ShoppingDatabase.TB_BOOK);

        Products p48 = new Products(R.drawable.php,"PHP",180,"E_Book",61,"A book for learning PHP & MySQL A detailed book for learning PHP and linking it with MySQL Prepared by Moataz . Previous: A book on programming websites. the best books to learn PHP programming language",0,(float) 3.5);
        db.insertProduct(p48,ShoppingDatabase.TB_BOOK);

         // laptop

        Products p51 = new Products(R.drawable.computer2,"computer",3830,"PC HP",52,"computer noc Barnes & Noble Retail and Samsung Electronics launched the new Samsung Galaxy Tab 4 Noc tablet in New York.",0,(float) 3.5);
        db.insertProduct(p51,ShoppingDatabase.TB_LAPTOP);

        Products p52 = new Products(R.drawable.laptophp1,"HP Laptop",8250,"HP",50,"HP Pavilion 15-dk1026ne Gaming laptop - 10th Intel Core i5-10300H, 8 GB RAM, 1TB HDD and 128GB SSD, NVIDIA GeForce GTX 1650 4GB GDDR6 Graphics, 15.6 inch FHD IPS, backlit keyboard, Dos - Shadow Black.",0,(float) 3.5);
        db.insertProduct(p52,ShoppingDatabase.TB_LAPTOP);

        Products p53 = new Products(R.drawable.laptopacer1,"Acer Laptop",6388,"Acer",44,"Acer A315/1005G1 Intel Core i3 Aspire 3 Notebook (10th Gen, 1.20GHz Up to 3.40GHz, 4GB DDR4 RAM, 1TB Hdd Storage, 15.6in Display, Win 10 Home, Red)",15,(float) 3.5);
        db.insertProduct(p53,ShoppingDatabase.TB_LAPTOP);

        Products p54 = new Products(R.drawable.laptopdell1,"Dell Laptop",7285,"Dell",15," Best Dell Laptop CPU: 11th Gen Intel Core i3/i5/i7 | GPU: Intel UHD/Intel Iris Xe | RAM: 8GB/16GB/32GB LPDDR4x.",0,(float) 3.5);
        db.insertProduct(p54,ShoppingDatabase.TB_LAPTOP);

        Products p55 = new Products(R.drawable.hardssd,"Hard SSD",180,"Kingston",44,"Hard Kingston 240GB A400 Sata 3 2.5 inch Internal Ssd Sa400S37,240G,HDD Replacement for Increase Performance Black.",0,(float) 3.5);
        db.insertProduct(p55,ShoppingDatabase.TB_LAPTOP);

        Products p56 = new Products(R.drawable.laptopapple,"Apple Laptop",9750,"Apple",47,"The laptops Apple 13″ MacBook Air features Apple’s first chip designed specifically for Mac. The Apple M1 integrates the CPU, GPU, Neural Engine, I/O, and more into a single system on a chip (SoC).",10,(float) 3.5);
        db.insertProduct(p56,ShoppingDatabase.TB_LAPTOP);

         // mobile
        Products p61 = new Products(R.drawable.huwawi1,"Huawei",1800,"Huawei",61,"HUAWEI Band 6 has a dynamic, lightweight design, 1.47”AMOLED display and 2-week battery life, with magnetic fast charging.",0,(float) 3.5);
        db.insertProduct(p61,ShoppingDatabase.TB_MOBILE);

        Products p62 = new Products(R.drawable.huwawi2,"Huawei",1650,"Huawei",68,"Huawei is a Chinese information and communications technology (ICT) company that specializes in telecommunications equipment.",0,(float) 3.5);
        db.insertProduct(p62,ShoppingDatabase.TB_MOBILE);

        Products p63 = new Products(R.drawable.iphone1,"iphone",2500,"Apple",71,"The iPhone is a smartphone made by Apple that combines a computer, iPod, digital camera and cellular phone into one device with a touchscreen interface..",0,(float) 3.5);
        db.insertProduct(p63,ShoppingDatabase.TB_MOBILE);

        Products p64 = new Products(R.drawable.infenx,"infinix",2200,"Infinix",15,"Infinix Zero 8 / X687 Silicone Case Transparent TPU Gorilla Anti-shock Shockproof Corners.",0,(float) 3.5);
        db.insertProduct(p64,ShoppingDatabase.TB_MOBILE);

        Products p65 = new Products(R.drawable.samsung1,"samsung",2800,"samsung",81,"Samsung Galaxy A52 Dual SIM - 6.5 inches, 8 GB RAM, 256 GB - Awesome Blue , 1800 EGP",15,(float) 3.5);
        db.insertProduct(p65,ShoppingDatabase.TB_MOBILE);

        Products p66 = new Products(R.drawable.realme1,"realme",1600,"Oppo",91,"Realme is a Chinese smartphone company established on May 4, 2018 (National Youth Day of China), by former Oppo vice-president and head of overseas business department.",0,(float) 3.5);
        db.insertProduct(p66,ShoppingDatabase.TB_MOBILE);

        Products p67 = new Products(R.drawable.oppo,"oppo",1900,"Oppo",31,"Guangdong Oppo Mobile Telecommunications Corp., Ltd, doing business as OPPO, is a Chinese consumer electronics and mobile communications company headquartered in Dongguan, Guangdong.",10,(float) 3.5);
        db.insertProduct(p67,ShoppingDatabase.TB_MOBILE);

        Products p68 = new Products(R.drawable.iphone2,"iphone",2900,"Apple",21,"The iPhone runs the iOS operating system, and in 2021 when the iPhone 13 was introduced, it offered up to 1 TB of storage and a 12-megapixel camera.",0,(float) 3.5);
        db.insertProduct(p68,ShoppingDatabase.TB_MOBILE);

         // beauty
        Products p71 = new Products(R.drawable.shampoo,"Shampoo",80,"Jordana",61,"Shampoo is typically in the form of a viscous liquid with some exception of waterless solid form such as a bar.",0,(float) 3.5);
        db.insertProduct(p71,ShoppingDatabase.TB_BEAUTY);

        Products p72 = new Products(R.drawable.toothpaste,"Toothpaste",50,"Jordana",68,"Toothpaste is a paste or gel dentifrice used with a toothbrush to clean and maintain the aesthetics and health of teeth.",0,(float) 3.5);
        db.insertProduct(p72,ShoppingDatabase.TB_BEAUTY);

        Products p73 = new Products(R.drawable.forsha,"forsha",70,"Flormar",71,"Flormar Foundation Brush - Black and White and Item dimensions LxWxH 6 x 5.6 x 12.3 centimeters and Item weight 15 Grams.",0,(float) 3.5);
        db.insertProduct(p73,ShoppingDatabase.TB_BEAUTY);

        Products p74 = new Products(R.drawable.makeup1,"Lipstick",30,"impala",15,"Lipstick advertisement suitable with all clothes and times and at reduced prices for a limited opportunity.",0,(float) 3.5);
        db.insertProduct(p74,ShoppingDatabase.TB_BEAUTY);

        Products p75 = new Products(R.drawable.perfume,"perfume",820,"yuesuo",81,"Mini Refillable Perfume Portable Atomizer Bottle Refillable Perfume Spray, Refill Pump Case for Traveling and Outgoing (5ml, 4 Pack).",15,(float) 3.5);
        db.insertProduct(p75,ShoppingDatabase.TB_BEAUTY);

        Products p76 = new Products(R.drawable.redlipstick,"Nail Polish",60,"Jordana",91,"Jordana Nail Polish, Purple Glam - Pack of 3, Available at a lower price from other sellers that may not offer free Prime shipping..",0,(float) 3.5);
        db.insertProduct(p76,ShoppingDatabase.TB_BEAUTY);

         // sport

        Products p81 = new Products(R.drawable.sports,"Football",80,"Adidas",61,"Super K Football Size 5 - Multi Color and Durable And Smooth Casing , It Is Suitable For Outdoor Playing.",0,(float) 3.5);
        db.insertProduct(p81,ShoppingDatabase.TB_SPORTS);

        Products p82 = new Products(R.drawable.sportshoes2,"sport shoes",150,"Adidas",68,"Adidas Nemeziz Messi .4 Textile Printed Three Back Stripe Lace-Up Turf Shoes for Boys 28.",10,(float) 3.5);
        db.insertProduct(p82,ShoppingDatabase.TB_SPORTS);

        Products p83 = new Products(R.drawable.pngegg,"t_shirt",170,"Adidas",71,"Real Madrid are the biggest and most successful football club in the world. Here is a brief history and some background information about Los Blancos and the Spanish city they call home.",0,(float) 3.5);
        db.insertProduct(p83,ShoppingDatabase.TB_SPORTS);

        Products p84 = new Products(R.drawable.pngfind,"American",130,"Adidas",15,"American ball is one of the popular and famous games in the whole world, you can now buy the ball and play with it.",0,(float) 3.5);
        db.insertProduct(p84,ShoppingDatabase.TB_SPORTS);

        Products p85 = new Products(R.drawable.pngegg2,"t_shirt",120,"Adidas",81,"Barcelona, the traditional centre of Catalan movements for independence, is the capital of both the province of Barcelona and the autonomous community of Catalonia.",15,(float) 3.5);
        db.insertProduct(p85,ShoppingDatabase.TB_SPORTS);

        Products p86 = new Products(R.drawable.titans,"American",160,"Adidas",91,"The helmet is now available to protect you from any risks while playing with friends Available with discounts for a limited time.",0,(float) 3.5);
        db.insertProduct(p86,ShoppingDatabase.TB_SPORTS);

         // car tool

        Products p91 = new Products(R.drawable.hammer,"hammer",80,"Shell",61,"The helmet is now available to protect you from any risks while playing with friends Available with discounts for a limited time.",0,(float) 3.5);
        db.insertProduct(p91,ShoppingDatabase.TB_CAR_TOOL);

        Products p92 = new Products(R.drawable.vehicleoil,"vehicle oil",150,"Shell",68,"Shell Engine Oil (4L) it has been packed under hygienic conditions, making it highly beneficial. Please read the instructions on the package before use.",10,(float) 3.5);
        db.insertProduct(p92,ShoppingDatabase.TB_CAR_TOOL);

        Products p93 = new Products(R.drawable.carwheel,"wheel",170,"Shell",71,"The rubber tire is the tire used in vehicles. It consists of a rubber disc and is installed to surround the edge of a metal ring to install the wheel.",0,(float) 3.5);
        db.insertProduct(p93,ShoppingDatabase.TB_CAR_TOOL);

        Products p94 = new Products(R.drawable.hondamotorcycle,"Honda",130,"Honda",15,"Honda Beat Car Motorcycle Spare part, auto parts, vehicle, tool.",0,(float) 3.5);
        db.insertProduct(p94,ShoppingDatabase.TB_CAR_TOOL);

        Products p95 = new Products(R.drawable.motorcycle,"motorcycle",220,"Bimota",81,"Motorcycle accessories Car Spare part, tires and auto parts, bicycle, happy Birthday Vector Images.",15,(float) 3.5);
        db.insertProduct(p95,ShoppingDatabase.TB_CAR_TOOL);

        Products p96 = new Products(R.drawable.steeringwheel,"Steering wheel",160,"Shell",91,"Car Steering wheel, Car Steering Wheel, car Accident, service.",0,(float) 3.5);
        db.insertProduct(p96,ShoppingDatabase.TB_CAR_TOOL);

    }

}
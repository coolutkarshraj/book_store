package com.io.bookstore.activity.homeActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.io.bookstore.Config;
import com.io.bookstore.R;
import com.io.bookstore.StaticData;
import com.io.bookstore.activity.authentication.LoginActivity;
import com.io.bookstore.activity.homeActivity.ui.cart.CartFragment;
import com.io.bookstore.activity.homeActivity.ui.deliveryAddress.DeliveryAddressFragment;
import com.io.bookstore.activity.homeActivity.ui.home.HomeFragment;
import com.io.bookstore.activity.homeActivity.ui.order.OrderFragment;
import com.io.bookstore.activity.profile.EditProfileFragment;
import com.io.bookstore.activity.profile.ProfileFragment;
import com.io.bookstore.fragment.BookListFragment;
import com.io.bookstore.fragment.BookstoresFragment;
import com.io.bookstore.fragment.BookstoresFragmentWithFilter;
import com.io.bookstore.fragment.CategoryListFragment;
import com.io.bookstore.fragment.EnrollCourseListFragment;
import com.io.bookstore.fragment.FavoriteItemsFragment;
import com.io.bookstore.listeners.ItemClickListner;
import com.io.bookstore.localStorage.DbHelper;
import com.io.bookstore.localStorage.LocalStorage;
import com.io.bookstore.model.bookListModel.CartLocalListResponseMode;
import com.io.bookstore.model.loginModel.LoginModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener, ItemClickListner {
    RelativeLayout ll_personal_info, ll_address, ll_payment, language, country, logout, ll_enroll_course;
    private DrawerLayout drawer;
    private NavigationView navigationView;
    private ImageView menu;
    Fragment currFrag;
    RadioButton radioButton;
    HomeFragment homeFragment;
    CartFragment cartFragment;
    OrderFragment orderFragment;
    CategoryListFragment categoryListFragment;
    FavoriteItemsFragment favoriteItemsFragment;
    FloatingActionButton fabSave;
    ImageView iv_cart;
    TextView nav_user, nav_Email,tv_logout;
    CircleImageView imageView;
    LinearLayout home, favfourite, order, profile;
    ImageView ivHome, ivHeart, ivCart, iv_profile;
    ProfileFragment profileFragment;
    ArrayList<CartLocalListResponseMode> item = new ArrayList<>();
    EditProfileFragment editProfileFragment;
    BookstoresFragment bookstoresFragment;
    BookstoresFragmentWithFilter bookstoresFragmentWithFilter;
    BookListFragment bookListFragment;
    EnrollCourseListFragment enrollCourseListFragment;
    DeliveryAddressFragment deliveryAddressFragment;
    LocalStorage localStorage;
    public static TextView tvcart;
    public static ArrayList<CartLocalListResponseMode> list = new ArrayList<>();
    DbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        bindListner();
        startWorking();

    }

    private void startWorking() {
        if (localStorage.getBoolean(LocalStorage.isCart)) {
            changeFrag(cartFragment, true);
        } else {

            startHome();
        }
    }

    private void startHome() {
        Drawable unwrappedDrawable = AppCompatResources.getDrawable(MainActivity.this, R.drawable.heart);
        assert unwrappedDrawable != null;
        Drawable wrappedDrawable = DrawableCompat.wrap(unwrappedDrawable);
        DrawableCompat.setTint(wrappedDrawable, getResources().getColor(R.color.gray));
        ivHeart.setImageResource(R.drawable.heart);

        Drawable unwrappedDrawable1 = AppCompatResources.getDrawable(MainActivity.this, R.drawable.ic_local_mall_black_24dp);
        assert unwrappedDrawable1 != null;
        Drawable wrappedDrawable1 = DrawableCompat.wrap(unwrappedDrawable1);
        DrawableCompat.setTint(wrappedDrawable1, getResources().getColor(R.color.gray));
        ivCart.setImageResource(R.drawable.ic_local_mall_black_24dp);

        Drawable unwrappedDrawable2 = AppCompatResources.getDrawable(MainActivity.this, R.drawable.profile);
        assert unwrappedDrawable2 != null;
        Drawable wrappedDrawable2 = DrawableCompat.wrap(unwrappedDrawable2);
        DrawableCompat.setTint(wrappedDrawable2, getResources().getColor(R.color.gray));
        iv_profile.setImageResource(R.drawable.profile);
        changeIconColor(MainActivity.this, R.drawable.ic_home, 0);

        changeFrag(homeFragment, true);
    }

    private void bindListner() {
        navigationView.setNavigationItemSelectedListener(MainActivity.this);
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.openDrawer(Gravity.LEFT);
            }
        });
        ll_personal_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeProfileColorIcon();
                drawer.closeDrawer(Gravity.LEFT);

            }
        });
        ll_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeFrag(deliveryAddressFragment, true);
                drawer.closeDrawer(Gravity.LEFT);
            }
        });
        ll_payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.closeDrawer(Gravity.LEFT);
            }
        });
        country.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.closeDrawer(Gravity.LEFT);
            }
        });
        language.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeLanguageDialog();
                drawer.closeDrawer(Gravity.LEFT);
            }
        });
        fabSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        iv_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeFrag(cartFragment, true);
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbHelper.deleteAll();
                localStorage.putBooleAan(LocalStorage.isLoggedIn, false);
                localStorage.putString(LocalStorage.token, "");
                localStorage.putDistributorProfile(null);
                Intent i = new Intent(MainActivity.this, LoginActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
            }
        });

        nav_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (nav_user.getText().toString().trim().equals("Hello Guest")) {
                    Intent i = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(i);
                }
            }
        });
        nav_Email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (nav_Email.getText().toString().trim().equals("Login or SignUp")) {
                    Intent i = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(i);
                }
            }
        });
        ll_enroll_course.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                changeFrag(enrollCourseListFragment, true);
                drawer.closeDrawer(Gravity.LEFT);
            }
        });
        footerClick();
    }

    private void changeLanguageDialog() {

        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;
        dialog.getWindow().setLayout((6 * width) / 7, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setContentView(R.layout.language_dialog);
        dialog.setTitle("");
        final Button Yes = (Button) dialog.findViewById(R.id.yes);
        final Button No = (Button) dialog.findViewById(R.id.no);
        final RadioGroup radioGroup = (RadioGroup) dialog.findViewById(R.id.radioGroup);
        final ImageView Clear = (ImageView) dialog.findViewById(R.id.clear);


        Yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedId = radioGroup.getCheckedRadioButtonId();
                radioButton = (RadioButton) dialog.findViewById(selectedId);
                if (selectedId == -1) {
                } else {
                    if (radioButton.getText().equals("English")) {
                        Locale locale = new Locale("en");
                        Locale.setDefault(locale);
                        Configuration config = new Configuration();
                        config.locale = locale;
                        getBaseContext().getResources().updateConfiguration(config, null);
                        dialog.dismiss();
                        initView();
                        bindListner();
                        startWorking();
                    } else {
                        Locale locale = new Locale("hi");
                        Locale.setDefault(locale);
                        Configuration config = new Configuration();
                        config.locale = locale;
                        getBaseContext().getResources().updateConfiguration(config, null);
                        dialog.dismiss();
                        initView();
                        bindListner();
                        startWorking();
                    }
                }
            }
        });
        No.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        Clear.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();


    }

    private void footerClick() {
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeHomeColorIcon();
            }
        });

        favfourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                changeFavouriteColorIcon();


            }
        });

        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeOrderColorIcon();
            }
        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeProfileColorIcon();

            }
        });


    }

    private void changeProfileColorIcon() {
        Drawable unwrappedDrawable = AppCompatResources.getDrawable(MainActivity.this, R.drawable.heart);
        assert unwrappedDrawable != null;
        Drawable wrappedDrawable = DrawableCompat.wrap(unwrappedDrawable);
        DrawableCompat.setTint(wrappedDrawable, getResources().getColor(R.color.gray));
        ivHeart.setImageResource(R.drawable.heart);

        Drawable unwrappedDrawable1 = AppCompatResources.getDrawable(MainActivity.this, R.drawable.ic_local_mall_black_24dp);
        assert unwrappedDrawable1 != null;
        Drawable wrappedDrawable1 = DrawableCompat.wrap(unwrappedDrawable1);
        DrawableCompat.setTint(wrappedDrawable1, getResources().getColor(R.color.gray));
        ivCart.setImageResource(R.drawable.ic_local_mall_black_24dp);

        Drawable unwrappedDrawable2 = AppCompatResources.getDrawable(MainActivity.this, R.drawable.ic_home);
        assert unwrappedDrawable2 != null;
        Drawable wrappedDrawable2 = DrawableCompat.wrap(unwrappedDrawable2);
        DrawableCompat.setTint(wrappedDrawable2, getResources().getColor(R.color.gray));
        ivHome.setImageResource(R.drawable.ic_home);
        changeIconColor(MainActivity.this, R.drawable.profile, 3);
        // changeFrag(profileFragment,true);
        this.getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_view, profileFragment)
                .addToBackStack(null)
                .commit();
    }

    private void changeOrderColorIcon() {
        Drawable unwrappedDrawable = AppCompatResources.getDrawable(MainActivity.this, R.drawable.heart);
        assert unwrappedDrawable != null;
        Drawable wrappedDrawable = DrawableCompat.wrap(unwrappedDrawable);
        DrawableCompat.setTint(wrappedDrawable, getResources().getColor(R.color.gray));
        ivHeart.setImageResource(R.drawable.heart);

        Drawable unwrappedDrawable1 = AppCompatResources.getDrawable(MainActivity.this, R.drawable.ic_home);
        assert unwrappedDrawable1 != null;
        Drawable wrappedDrawable1 = DrawableCompat.wrap(unwrappedDrawable1);
        DrawableCompat.setTint(wrappedDrawable1, getResources().getColor(R.color.gray));
        ivHome.setImageResource(R.drawable.ic_home);

        Drawable unwrappedDrawable2 = AppCompatResources.getDrawable(MainActivity.this, R.drawable.profile);
        assert unwrappedDrawable2 != null;
        Drawable wrappedDrawable2 = DrawableCompat.wrap(unwrappedDrawable2);
        DrawableCompat.setTint(wrappedDrawable2, getResources().getColor(R.color.gray));
        iv_profile.setImageResource(R.drawable.profile);
        changeIconColor(MainActivity.this, R.drawable.ic_local_mall_black_24dp, 2);
        changeFrag(orderFragment, true);
    }

    private void changeFavouriteColorIcon() {
        Drawable unwrappedDrawable = AppCompatResources.getDrawable(MainActivity.this, R.drawable.ic_home);
        assert unwrappedDrawable != null;
        Drawable wrappedDrawable = DrawableCompat.wrap(unwrappedDrawable);
        DrawableCompat.setTint(wrappedDrawable, getResources().getColor(R.color.gray));
        ivHome.setImageResource(R.drawable.ic_home);

        Drawable unwrappedDrawable1 = AppCompatResources.getDrawable(MainActivity.this, R.drawable.ic_local_mall_black_24dp);
        assert unwrappedDrawable1 != null;
        Drawable wrappedDrawable1 = DrawableCompat.wrap(unwrappedDrawable1);
        DrawableCompat.setTint(wrappedDrawable1, getResources().getColor(R.color.gray));
        ivCart.setImageResource(R.drawable.ic_local_mall_black_24dp);

        Drawable unwrappedDrawable2 = AppCompatResources.getDrawable(MainActivity.this, R.drawable.profile);
        assert unwrappedDrawable2 != null;
        Drawable wrappedDrawable2 = DrawableCompat.wrap(unwrappedDrawable2);
        DrawableCompat.setTint(wrappedDrawable2, getResources().getColor(R.color.gray));
        iv_profile.setImageResource(R.drawable.profile);

        changeIconColor(MainActivity.this, R.drawable.heart, 1);
        changeFrag(favoriteItemsFragment, true);
    }

    private void changeHomeColorIcon() {
        Drawable unwrappedDrawable = AppCompatResources.getDrawable(MainActivity.this, R.drawable.heart);
        assert unwrappedDrawable != null;
        Drawable wrappedDrawable = DrawableCompat.wrap(unwrappedDrawable);
        DrawableCompat.setTint(wrappedDrawable, getResources().getColor(R.color.gray));
        ivHeart.setImageResource(R.drawable.heart);

        Drawable unwrappedDrawable1 = AppCompatResources.getDrawable(MainActivity.this, R.drawable.ic_local_mall_black_24dp);
        assert unwrappedDrawable1 != null;
        Drawable wrappedDrawable1 = DrawableCompat.wrap(unwrappedDrawable1);
        DrawableCompat.setTint(wrappedDrawable1, getResources().getColor(R.color.gray));
        ivCart.setImageResource(R.drawable.ic_local_mall_black_24dp);

        Drawable unwrappedDrawable2 = AppCompatResources.getDrawable(MainActivity.this, R.drawable.profile);
        assert unwrappedDrawable2 != null;
        Drawable wrappedDrawable2 = DrawableCompat.wrap(unwrappedDrawable2);
        DrawableCompat.setTint(wrappedDrawable2, getResources().getColor(R.color.gray));
        iv_profile.setImageResource(R.drawable.profile);
        changeIconColor(MainActivity.this, R.drawable.ic_home, 0);

        changeFrag(homeFragment, true);
    }

    private void changeIconColor(Context context, int drawable, int i) {
        Drawable unwrappedDrawable = AppCompatResources.getDrawable(context, drawable);
        assert unwrappedDrawable != null;
        Drawable wrappedDrawable = DrawableCompat.wrap(unwrappedDrawable);
        DrawableCompat.setTint(wrappedDrawable, getResources().getColor(R.color.colorAccent));
        if (i == 0) {
            ivHome.setImageResource(drawable);
        } else if (i == 1) {
            ivHeart.setImageResource(drawable);
        } else if (i == 2) {
            ivCart.setImageResource(drawable);
        } else if (i == 3) {
            iv_profile.setImageResource(drawable);
        }

    }


    private void initView() {
        localStorage = new LocalStorage(this);
        ivHome = findViewById(R.id.ivHome);
        ivHeart = findViewById(R.id.ivHeart);
        ivCart = findViewById(R.id.ivCart);
        iv_profile = findViewById(R.id.iv_profile);
        drawer = findViewById(R.id.drawer_layout);
        iv_cart = findViewById(R.id.iv_cart);
        home = findViewById(R.id.home);
        favfourite = findViewById(R.id.favfourite);
        order = findViewById(R.id.order);
        profile = findViewById(R.id.profile);
        fabSave = findViewById(R.id.fabSave);
        homeFragment = new HomeFragment();
        cartFragment = new CartFragment();
        orderFragment = new OrderFragment();
        favoriteItemsFragment = new FavoriteItemsFragment();
        deliveryAddressFragment = new DeliveryAddressFragment();
        dbHelper = new DbHelper(this);
        navigationView = findViewById(R.id.nav_view);
        ll_enroll_course = findViewById(R.id.ll_course);
        tv_logout = findViewById(R.id.tv_logout);
        menu = findViewById(R.id.menu);
        logout = (RelativeLayout) findViewById(R.id.ll_logout);
        nav_user = (TextView) findViewById(R.id.nav_username);
        nav_Email = (TextView) findViewById(R.id.nav_email);
        imageView = (CircleImageView) findViewById(R.id.nav_profile_iv);
        ll_personal_info = findViewById(R.id.ll_personal_info);
        ll_address = findViewById(R.id.ll_address);
        ll_payment = findViewById(R.id.ll_payment);
        language = findViewById(R.id.language);
        tvcart = findViewById(R.id.tv_cart);
        country = findViewById(R.id.country);
        enrollCourseListFragment = new EnrollCourseListFragment();
        profileFragment = new ProfileFragment();
        categoryListFragment = new CategoryListFragment();
        bookListFragment = new BookListFragment();
        bookstoresFragmentWithFilter = new BookstoresFragmentWithFilter();
        navigationHeader();
        getSqliteData1();
    }


    public void editProfile(View v) {
        editProfileFragment = new EditProfileFragment();
        this.getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_view, editProfileFragment)
                .addToBackStack(null)
                .commit();
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();

        return true;
    }

    private void changeFrag(Fragment fragment, boolean addToBack) {

        currFrag = fragment;
        FragmentTransaction m = getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_view, fragment);
        if (addToBack) {
            m.addToBackStack(null);
            m.setCustomAnimations(R.anim.fade_in,
                    R.anim.fade_out);
        }
        m.commit();
    }

    @Override
    public void onClick(int position) {
        if (position == 1) {
            bookstoresFragment = new BookstoresFragment();
            MainActivity.this.getSupportFragmentManager().beginTransaction()
                    .replace(R.id.content_view, bookstoresFragment)
                    .addToBackStack(null)
                    .commit();
        }
        if (position == 2) {
            MainActivity.this.getSupportFragmentManager().beginTransaction()
                    .replace(R.id.content_view, categoryListFragment)
                    .addToBackStack(null)
                    .commit();
        }
        if (position == 3) {
            MainActivity.this.getSupportFragmentManager().beginTransaction()
                    .replace(R.id.content_view, bookListFragment)
                    .addToBackStack(null)
                    .commit();
        }
        if (position == 4) {
            MainActivity.this.getSupportFragmentManager().beginTransaction()
                    .replace(R.id.content_view, bookstoresFragmentWithFilter)
                    .addToBackStack(null)
                    .commit();
        }
    }

    @SuppressLint("ResourceType")
    private void navigationHeader() {
        LoginModel loginModel = localStorage.getUserProfile();
        if (loginModel == null) {
            nav_user.setText("Hello Guest");
            nav_Email.setText("Login or SignUp");
            tv_logout.setText("Login or SignUp");
            Glide.with(getApplicationContext()).load(R.drawable.person_logo).into(imageView);
        } else {
            tv_logout.setText(R.string.logout);
            nav_user.setText(localStorage.getUserProfile().getData().getUser().getName());
            nav_Email.setText(localStorage.getUserProfile().getData().getUser().getEmail());
            Glide.with(getApplicationContext()).load(Config.imageUrl + localStorage.getUserProfile().getData().getUser().getAvatarPath()).into(imageView);

        }
    }

    private void getSqliteData1() {
        DbHelper dbHelper;
        dbHelper = new DbHelper(this);
        Cursor cursor = dbHelper.getData();
        if (cursor.getCount() == 0) {
            Log.e("Error", "no Data");
            return;
        }
        JSONArray resultSet = new JSONArray();
        JSONObject returnObj = new JSONObject();

        cursor.moveToFirst();
        while (cursor.isAfterLast() == false) {

            int totalColumn = cursor.getColumnCount();
            JSONObject rowObject = new JSONObject();

            for (int i = 0; i < totalColumn; i++) {
                if (cursor.getColumnName(i) != null) {
                    try {
                        if (cursor.getString(i) != null) {
                            Log.d("TAG_NAME2", cursor.getString(i));
                            rowObject.put(cursor.getColumnName(i), cursor.getString(i));
                        } else {
                            rowObject.put(cursor.getColumnName(i), "");
                        }
                    } catch (Exception e) {
                        Log.d("TAG_NAME1", e.getMessage());
                    }
                }
            }
            resultSet.put(rowObject);
            cursor.moveToNext();
        }
        cursor.close();
        String datajson = resultSet.toString();
        datajson.replaceAll("\\\\", "");
        Log.d("datajson", datajson);
        StaticData.CartData = datajson;
        cartData(datajson);
    }

    private void cartData(String datajson) {
        JSONArray jArray = null;

        try {

            jArray = new JSONArray(datajson);
            if (jArray == null || jArray.length() == 0) {
                tvcart.setVisibility(View.GONE);
            } else {
                tvcart.setVisibility(View.VISIBLE);
                tvcart.setText("" + jArray.length());
                for (int i = 0; i < jArray.length(); i++) {
                    JSONObject json_data = jArray.getJSONObject(i);
                    CartLocalListResponseMode shoppingBagModel = new CartLocalListResponseMode();
                    shoppingBagModel.setId(json_data.getString("Id"));
                    shoppingBagModel.setName(json_data.getString("Name"));
                    shoppingBagModel.setQuantity(json_data.getString("Quantity"));
                    shoppingBagModel.setPrice(json_data.getString("Price"));
                    shoppingBagModel.setImage(json_data.getString("Image"));
                    shoppingBagModel.setAvailibleQty(json_data.getString("avalible"));
                    shoppingBagModel.setPID(json_data.getString("P_ID"));
                    shoppingBagModel.setGst(json_data.getString("gstPrice"));
                    list.add(shoppingBagModel);

                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }



    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }

    }
}

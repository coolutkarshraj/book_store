package com.io.bookstore.bookStore;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import de.hdodenhof.circleimageview.CircleImageView;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.io.bookstore.Config;
import com.io.bookstore.R;
import com.io.bookstore.activity.authentication.LoginActivity;
import com.io.bookstore.activity.homeActivity.MainActivity;
import com.io.bookstore.activity.homeActivity.ui.cart.CartFragment;
import com.io.bookstore.activity.homeActivity.ui.deliveryAddress.DeliveryAddressFragment;
import com.io.bookstore.activity.homeActivity.ui.home.HomeFragment;
import com.io.bookstore.activity.homeActivity.ui.order.OrderFragment;
import com.io.bookstore.activity.profile.EditProfileFragment;
import com.io.bookstore.activity.profile.ProfileFragment;
import com.io.bookstore.apicaller.ApiCaller;
import com.io.bookstore.fragment.BookListFragment;
import com.io.bookstore.fragment.BookstoresFragment;
import com.io.bookstore.fragment.BookstoresFragmentWithFilter;
import com.io.bookstore.fragment.CategoryListFragment;
import com.io.bookstore.fragment.FavoriteItemsFragment;
import com.io.bookstore.fragment.bookStoreFragment.HomeBookFragment;
import com.io.bookstore.fragment.bookStoreFragment.OrderListBookFragment;
import com.io.bookstore.fragment.bookStoreFragment.ProfileAdminFragment;
import com.io.bookstore.listeners.ItemClickListner;
import com.io.bookstore.localStorage.LocalStorage;
import com.io.bookstore.model.LogoutResponseModel;
import com.io.bookstore.utility.NewProgressBar;
import com.io.bookstore.utility.Utils;
import com.io.bookstore.utility.userOnlineInfo;
import com.koushikdutta.async.future.FutureCallback;

import java.util.Locale;

public class BookStoreMainActivity extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener, ItemClickListner {
    RelativeLayout ll_personal_info, ll_address, ll_payment, language, country,logout;
    private DrawerLayout drawer;
    private NavigationView navigationView;
    private ImageView menu;
    Fragment currFrag;
    RadioButton radioButton;
    TextView nav_user, nav_Email;
    CircleImageView imageView;
    userOnlineInfo user;
    NewProgressBar dialog;

    HomeBookFragment homeBookFragment;
    OrderListBookFragment orderListBookFragment;
    ProfileAdminFragment profileAdminFragment;

    CategoryListFragment categoryListFragment;
    FavoriteItemsFragment favoriteItemsFragment;
    FloatingActionButton fabSave;
    ImageView iv_cart;
    LinearLayout home, favfourite, order, profile;
    ImageView ivHome, ivHeart, ivCart, iv_profile;
    ProfileFragment profileFragment;
    EditProfileFragment editProfileFragment;
    BookstoresFragment bookstoresFragment;
    BookstoresFragmentWithFilter bookstoresFragmentWithFilter;
    BookListFragment bookListFragment;
    DeliveryAddressFragment deliveryAddressFragment;
    LocalStorage localStorage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_book_store);
        initView();
        bindListner();
        startWorking();

    }

    private void startWorking() {
      /*  if(localStorage.getBoolean(LocalStorage.isCart)){
            changeFrag(cartFragment,true);
        }else {*/

        startHome();
        //}
    }

    private void startHome() {
        Drawable unwrappedDrawable = AppCompatResources.getDrawable(BookStoreMainActivity.this, R.drawable.heart);
        assert unwrappedDrawable != null;
        Drawable wrappedDrawable = DrawableCompat.wrap(unwrappedDrawable);
        DrawableCompat.setTint(wrappedDrawable, getResources().getColor(R.color.gray));
        ivHeart.setImageResource(R.drawable.heart);

        Drawable unwrappedDrawable1 = AppCompatResources.getDrawable(BookStoreMainActivity.this, R.drawable.ic_local_mall_black_24dp);
        assert unwrappedDrawable1 != null;
        Drawable wrappedDrawable1 = DrawableCompat.wrap(unwrappedDrawable1);
        DrawableCompat.setTint(wrappedDrawable1, getResources().getColor(R.color.gray));
        ivCart.setImageResource(R.drawable.ic_local_mall_black_24dp);

        Drawable unwrappedDrawable2 = AppCompatResources.getDrawable(BookStoreMainActivity.this, R.drawable.profile);
        assert unwrappedDrawable2 != null;
        Drawable wrappedDrawable2 = DrawableCompat.wrap(unwrappedDrawable2);
        DrawableCompat.setTint(wrappedDrawable2, getResources().getColor(R.color.gray));
        iv_profile.setImageResource(R.drawable.profile);
        changeIconColor(BookStoreMainActivity.this, R.drawable.ic_home, 0);

       /* if(localStorage.getInt(LocalStorage.role) ==0){
            changeFrag(homeBookFragment, true);
        }else {*/
        changeFrag(homeBookFragment, true);
        // }
    }

    private void bindListner() {
        navigationView.setNavigationItemSelectedListener(BookStoreMainActivity.this);
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
                changeFrag(orderListBookFragment, true);
                drawer.closeDrawer(Gravity.LEFT);
            }
        });
        ll_payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeFrag(orderListBookFragment, true);
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
                drawer.closeDrawer(Gravity.LEFT);
                changeLanguageDialog();
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
                // changeFrag(cartFragment,true);
            }
        });

        footerClick();
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

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logoutApiCall();
            }
        });

    }

    private void logoutApiCall() {
        if (user.isOnline(this)) {
            dialog = new NewProgressBar(this);
            dialog.show();
            final LocalStorage localStorage = new LocalStorage(this);
            ApiCaller.logout(this, Config.Url.storelogout+"/"+localStorage.getInt(LocalStorage.userId),
                    new FutureCallback<LogoutResponseModel>() {
                        @Override
                        public void onCompleted(Exception e, LogoutResponseModel result) {
                            if (e != null) {
                                dialog.dismiss();
                                Utils.showAlertDialog(BookStoreMainActivity.this, "Something Went Wrong");
                                return;
                            }

                            if (result != null) {
                                if (result.getStatus() ==true) {
                                    dialog.dismiss();
                                    Toast.makeText(BookStoreMainActivity.this, "" + result.getMessage(), Toast.LENGTH_SHORT).show();
                                    localStorage.putBooleAan(LocalStorage.isLoggedIn, false);
                                    localStorage.putString(LocalStorage.token, "");
                                    localStorage.putInt(LocalStorage.role,0);
                                    localStorage.putDistributorProfile(null);
                                    Intent i = new Intent(BookStoreMainActivity.this, LoginActivity.class);
                                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(i);
                                }
                            }

                        }
                    });

        } else {
            Utils.showAlertDialog(BookStoreMainActivity.this, "No Internet Connection");
        }
    }

    private void changeProfileColorIcon() {
        Drawable unwrappedDrawable = AppCompatResources.getDrawable(BookStoreMainActivity.this, R.drawable.heart);
        assert unwrappedDrawable != null;
        Drawable wrappedDrawable = DrawableCompat.wrap(unwrappedDrawable);
        DrawableCompat.setTint(wrappedDrawable, getResources().getColor(R.color.gray));
        ivHeart.setImageResource(R.drawable.heart);

        Drawable unwrappedDrawable1 = AppCompatResources.getDrawable(BookStoreMainActivity.this, R.drawable.ic_local_mall_black_24dp);
        assert unwrappedDrawable1 != null;
        Drawable wrappedDrawable1 = DrawableCompat.wrap(unwrappedDrawable1);
        DrawableCompat.setTint(wrappedDrawable1, getResources().getColor(R.color.gray));
        ivCart.setImageResource(R.drawable.ic_local_mall_black_24dp);

        Drawable unwrappedDrawable2 = AppCompatResources.getDrawable(BookStoreMainActivity.this, R.drawable.ic_home);
        assert unwrappedDrawable2 != null;
        Drawable wrappedDrawable2 = DrawableCompat.wrap(unwrappedDrawable2);
        DrawableCompat.setTint(wrappedDrawable2, getResources().getColor(R.color.gray));
        ivHome.setImageResource(R.drawable.ic_home);
        changeIconColor(BookStoreMainActivity.this, R.drawable.profile, 3);
        changeFrag(profileAdminFragment, true);
       /* this.getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_view, profileFragment)
                .addToBackStack(null)
                .commit();*/
    }

    private void changeOrderColorIcon() {
        Drawable unwrappedDrawable = AppCompatResources.getDrawable(BookStoreMainActivity.this, R.drawable.heart);
        assert unwrappedDrawable != null;
        Drawable wrappedDrawable = DrawableCompat.wrap(unwrappedDrawable);
        DrawableCompat.setTint(wrappedDrawable, getResources().getColor(R.color.gray));
        ivHeart.setImageResource(R.drawable.heart);

        Drawable unwrappedDrawable1 = AppCompatResources.getDrawable(BookStoreMainActivity.this, R.drawable.ic_home);
        assert unwrappedDrawable1 != null;
        Drawable wrappedDrawable1 = DrawableCompat.wrap(unwrappedDrawable1);
        DrawableCompat.setTint(wrappedDrawable1, getResources().getColor(R.color.gray));
        ivHome.setImageResource(R.drawable.ic_home);

        Drawable unwrappedDrawable2 = AppCompatResources.getDrawable(BookStoreMainActivity.this, R.drawable.profile);
        assert unwrappedDrawable2 != null;
        Drawable wrappedDrawable2 = DrawableCompat.wrap(unwrappedDrawable2);
        DrawableCompat.setTint(wrappedDrawable2, getResources().getColor(R.color.gray));
        iv_profile.setImageResource(R.drawable.profile);
        changeIconColor(BookStoreMainActivity.this, R.drawable.ic_local_mall_black_24dp, 2);


      /*  if(localStorage.getInt(LocalStorage.role) ==0){
            changeFrag(orderListBookFragment, true);
        }else {*/
        changeFrag(orderListBookFragment, true);
        // }
    }

    private void changeFavouriteColorIcon() {
        Drawable unwrappedDrawable = AppCompatResources.getDrawable(BookStoreMainActivity.this, R.drawable.ic_home);
        assert unwrappedDrawable != null;
        Drawable wrappedDrawable = DrawableCompat.wrap(unwrappedDrawable);
        DrawableCompat.setTint(wrappedDrawable, getResources().getColor(R.color.gray));
        ivHome.setImageResource(R.drawable.ic_home);

        Drawable unwrappedDrawable1 = AppCompatResources.getDrawable(BookStoreMainActivity.this, R.drawable.ic_local_mall_black_24dp);
        assert unwrappedDrawable1 != null;
        Drawable wrappedDrawable1 = DrawableCompat.wrap(unwrappedDrawable1);
        DrawableCompat.setTint(wrappedDrawable1, getResources().getColor(R.color.gray));
        ivCart.setImageResource(R.drawable.ic_local_mall_black_24dp);

        Drawable unwrappedDrawable2 = AppCompatResources.getDrawable(BookStoreMainActivity.this, R.drawable.profile);
        assert unwrappedDrawable2 != null;
        Drawable wrappedDrawable2 = DrawableCompat.wrap(unwrappedDrawable2);
        DrawableCompat.setTint(wrappedDrawable2, getResources().getColor(R.color.gray));
        iv_profile.setImageResource(R.drawable.profile);

        changeIconColor(BookStoreMainActivity.this, R.drawable.heart, 1);
        changeFrag(favoriteItemsFragment, true);
    }

    private void changeHomeColorIcon() {
        Drawable unwrappedDrawable = AppCompatResources.getDrawable(BookStoreMainActivity.this, R.drawable.heart);
        assert unwrappedDrawable != null;
        Drawable wrappedDrawable = DrawableCompat.wrap(unwrappedDrawable);
        DrawableCompat.setTint(wrappedDrawable, getResources().getColor(R.color.gray));
        ivHeart.setImageResource(R.drawable.heart);

        Drawable unwrappedDrawable1 = AppCompatResources.getDrawable(BookStoreMainActivity.this, R.drawable.ic_local_mall_black_24dp);
        assert unwrappedDrawable1 != null;
        Drawable wrappedDrawable1 = DrawableCompat.wrap(unwrappedDrawable1);
        DrawableCompat.setTint(wrappedDrawable1, getResources().getColor(R.color.gray));
        ivCart.setImageResource(R.drawable.ic_local_mall_black_24dp);

        Drawable unwrappedDrawable2 = AppCompatResources.getDrawable(BookStoreMainActivity.this, R.drawable.profile);
        assert unwrappedDrawable2 != null;
        Drawable wrappedDrawable2 = DrawableCompat.wrap(unwrappedDrawable2);
        DrawableCompat.setTint(wrappedDrawable2, getResources().getColor(R.color.gray));
        iv_profile.setImageResource(R.drawable.profile);
        changeIconColor(BookStoreMainActivity.this, R.drawable.ic_home, 0);


     /*   if(localStorage.getInt(LocalStorage.role) ==0){
            changeFrag(homeBookFragment, true);
        }else {*/
        changeFrag(homeBookFragment, true);
        // }
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
    user = new userOnlineInfo();
        nav_user = (TextView) findViewById(R.id.nav_username);
        nav_Email = (TextView) findViewById(R.id.nav_email);
        imageView = (CircleImageView) findViewById(R.id.nav_profile_iv);
        favoriteItemsFragment = new FavoriteItemsFragment();
        deliveryAddressFragment = new DeliveryAddressFragment();
        homeBookFragment = new HomeBookFragment();
        orderListBookFragment = new OrderListBookFragment();
        profileAdminFragment = new ProfileAdminFragment();
        navigationView = findViewById(R.id.nav_view);
        menu = findViewById(R.id.menu);
        logout = findViewById(R.id.logout);
        ll_personal_info = findViewById(R.id.ll_personal_info);
        ll_address = findViewById(R.id.ll_address);
        ll_payment = findViewById(R.id.ll_payment);
        language = findViewById(R.id.language);
        country = findViewById(R.id.country);
        profileFragment = new ProfileFragment();
        categoryListFragment = new CategoryListFragment();
        bookListFragment = new BookListFragment();
        bookstoresFragmentWithFilter = new BookstoresFragmentWithFilter();
        navigationHeader();
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
            BookStoreMainActivity.this.getSupportFragmentManager().beginTransaction()
                    .replace(R.id.content_view, homeBookFragment)
                    .addToBackStack(null)
                    .commit();
        }
        if (position == 2) {
            BookStoreMainActivity.this.getSupportFragmentManager().beginTransaction()
                    .replace(R.id.content_view, orderListBookFragment)
                    .addToBackStack(null)
                    .commit();
        }
        if (position == 3) {
            BookStoreMainActivity.this.getSupportFragmentManager().beginTransaction()
                    .replace(R.id.content_view, profileAdminFragment)
                    .addToBackStack(null)
                    .commit();
        }
        if (position == 4) {
            BookStoreMainActivity.this.getSupportFragmentManager().beginTransaction()
                    .replace(R.id.content_view, bookstoresFragmentWithFilter)
                    .addToBackStack(null)
                    .commit();
        }
    }

    private void navigationHeader() {
        if(localStorage ==null){
            nav_user.setText("");
            nav_Email.setText("");
            Glide.with(getApplicationContext()).load(R.drawable.person_logo).into(imageView);
        }else {
            nav_user.setText(localStorage.getUserProfile().getData().getUser().getName());
            nav_Email.setText(localStorage.getUserProfile().getData().getUser().getEmail());
            Glide.with(getApplicationContext()).load(Config.imageUrl + localStorage.getUserProfile().getData().getUser().getAvatarPath()).into(imageView);

        }


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
                        startActivity(new Intent(BookStoreMainActivity.this,BookStoreMainActivity.class));
                    } else {
                        Locale locale = new Locale("hi");
                        Locale.setDefault(locale);
                        Configuration config = new Configuration();
                        config.locale = locale;
                        getBaseContext().getResources().updateConfiguration(config, null);
                        dialog.dismiss();
                        startActivity(new Intent(BookStoreMainActivity.this,BookStoreMainActivity.class));
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


    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }

    }
}

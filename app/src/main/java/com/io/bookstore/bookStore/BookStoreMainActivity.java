package com.io.bookstore.bookStore;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.io.bookstore.R;
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
import com.io.bookstore.fragment.FavoriteItemsFragment;
import com.io.bookstore.fragment.bookStoreFragment.HomeBookFragment;
import com.io.bookstore.fragment.bookStoreFragment.OrderListBookFragment;
import com.io.bookstore.fragment.bookStoreFragment.ProfileAdminFragment;
import com.io.bookstore.listeners.ItemClickListner;
import com.io.bookstore.localStorage.LocalStorage;

public class BookStoreMainActivity extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener, ItemClickListner {
    LinearLayout ll_personal_info,ll_address,ll_payment,language,country;
    private DrawerLayout drawer;
    private NavigationView navigationView;
    private ImageView menu;
    Fragment currFrag;
    HomeFragment homeFragment;
    CartFragment cartFragment;
    OrderFragment orderFragment;
     HomeBookFragment homeBookFragment;
     OrderListBookFragment orderListBookFragment;
     ProfileAdminFragment profileAdminFragment;

    CategoryListFragment categoryListFragment;
    FavoriteItemsFragment favoriteItemsFragment;
    FloatingActionButton fabSave;
    ImageView iv_cart;
    LinearLayout home,favfourite,order,profile;
    ImageView ivHome,ivHeart,ivCart,iv_profile;
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
        if(localStorage.getBoolean(LocalStorage.isCart)){
            changeFrag(cartFragment,true);
        }else {

            startHome();
        }
    }

    private void startHome() {
        Drawable unwrappedDrawable = AppCompatResources.getDrawable(BookStoreMainActivity.this, R.drawable.heart);
        assert unwrappedDrawable != null;
        Drawable wrappedDrawable = DrawableCompat.wrap(unwrappedDrawable);
        DrawableCompat.setTint(wrappedDrawable, getResources().getColor(R.color.gray));
        ivHeart.setImageResource(R.drawable.heart);

        Drawable unwrappedDrawable1 = AppCompatResources.getDrawable(BookStoreMainActivity.this, R.drawable.cart);
        assert unwrappedDrawable1 != null;
        Drawable wrappedDrawable1 = DrawableCompat.wrap(unwrappedDrawable1);
        DrawableCompat.setTint(wrappedDrawable1, getResources().getColor(R.color.gray));
        ivCart.setImageResource(R.drawable.cart);

        Drawable unwrappedDrawable2 = AppCompatResources.getDrawable(BookStoreMainActivity.this, R.drawable.profile);
        assert unwrappedDrawable2 != null;
        Drawable wrappedDrawable2 = DrawableCompat.wrap(unwrappedDrawable2);
        DrawableCompat.setTint(wrappedDrawable2, getResources().getColor(R.color.gray));
        iv_profile.setImageResource(R.drawable.profile);
        changeIconColor(BookStoreMainActivity.this, R.drawable.ic_home,0);

        if(localStorage.getInt(LocalStorage.role) ==0){
            changeFrag(homeBookFragment, true);
        }else {
            changeFrag(homeFragment, true);
        }
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
                changeFrag(deliveryAddressFragment,true);
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
                changeFrag(cartFragment,true);
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



    }

    private void changeProfileColorIcon() {
        Drawable unwrappedDrawable = AppCompatResources.getDrawable(BookStoreMainActivity.this, R.drawable.heart);
        assert unwrappedDrawable != null;
        Drawable wrappedDrawable = DrawableCompat.wrap(unwrappedDrawable);
        DrawableCompat.setTint(wrappedDrawable, getResources().getColor(R.color.gray));
        ivHeart.setImageResource(R.drawable.heart);

        Drawable unwrappedDrawable1 = AppCompatResources.getDrawable(BookStoreMainActivity.this, R.drawable.cart);
        assert unwrappedDrawable1 != null;
        Drawable wrappedDrawable1 = DrawableCompat.wrap(unwrappedDrawable1);
        DrawableCompat.setTint(wrappedDrawable1, getResources().getColor(R.color.gray));
        ivCart.setImageResource(R.drawable.cart);

        Drawable unwrappedDrawable2 = AppCompatResources.getDrawable(BookStoreMainActivity.this, R.drawable.ic_home);
        assert unwrappedDrawable2 != null;
        Drawable wrappedDrawable2 = DrawableCompat.wrap(unwrappedDrawable2);
        DrawableCompat.setTint(wrappedDrawable2, getResources().getColor(R.color.gray));
        ivHome.setImageResource(R.drawable.ic_home);
        changeIconColor(BookStoreMainActivity.this, R.drawable.profile,3);
       // changeFrag(profileFragment,true);
        this.getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_view, profileFragment)
                .addToBackStack(null)
                .commit();
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
        changeIconColor(BookStoreMainActivity.this, R.drawable.cart,2);
        changeFrag(orderFragment,true);
    }

    private void changeFavouriteColorIcon() {
        Drawable unwrappedDrawable = AppCompatResources.getDrawable(BookStoreMainActivity.this, R.drawable.ic_home);
        assert unwrappedDrawable != null;
        Drawable wrappedDrawable = DrawableCompat.wrap(unwrappedDrawable);
        DrawableCompat.setTint(wrappedDrawable, getResources().getColor(R.color.gray));
        ivHome.setImageResource(R.drawable.ic_home);

        Drawable unwrappedDrawable1 = AppCompatResources.getDrawable(BookStoreMainActivity.this, R.drawable.cart);
        assert unwrappedDrawable1 != null;
        Drawable wrappedDrawable1 = DrawableCompat.wrap(unwrappedDrawable1);
        DrawableCompat.setTint(wrappedDrawable1, getResources().getColor(R.color.gray));
        ivCart.setImageResource(R.drawable.cart);

        Drawable unwrappedDrawable2 = AppCompatResources.getDrawable(BookStoreMainActivity.this, R.drawable.profile);
        assert unwrappedDrawable2 != null;
        Drawable wrappedDrawable2 = DrawableCompat.wrap(unwrappedDrawable2);
        DrawableCompat.setTint(wrappedDrawable2, getResources().getColor(R.color.gray));
        iv_profile.setImageResource(R.drawable.profile);

        changeIconColor(BookStoreMainActivity.this, R.drawable.heart,1);
        changeFrag(favoriteItemsFragment,true);
    }

    private void changeHomeColorIcon() {
        Drawable unwrappedDrawable = AppCompatResources.getDrawable(BookStoreMainActivity.this, R.drawable.heart);
        assert unwrappedDrawable != null;
        Drawable wrappedDrawable = DrawableCompat.wrap(unwrappedDrawable);
        DrawableCompat.setTint(wrappedDrawable, getResources().getColor(R.color.gray));
        ivHeart.setImageResource(R.drawable.heart);

        Drawable unwrappedDrawable1 = AppCompatResources.getDrawable(BookStoreMainActivity.this, R.drawable.cart);
        assert unwrappedDrawable1 != null;
        Drawable wrappedDrawable1 = DrawableCompat.wrap(unwrappedDrawable1);
        DrawableCompat.setTint(wrappedDrawable1, getResources().getColor(R.color.gray));
        ivCart.setImageResource(R.drawable.cart);

        Drawable unwrappedDrawable2 = AppCompatResources.getDrawable(BookStoreMainActivity.this, R.drawable.profile);
        assert unwrappedDrawable2 != null;
        Drawable wrappedDrawable2 = DrawableCompat.wrap(unwrappedDrawable2);
        DrawableCompat.setTint(wrappedDrawable2, getResources().getColor(R.color.gray));
        iv_profile.setImageResource(R.drawable.profile);
        changeIconColor(BookStoreMainActivity.this, R.drawable.ic_home,0);


        if(localStorage.getInt(LocalStorage.role) ==0){
            changeFrag(homeBookFragment, true);
        }else {
            changeFrag(homeFragment, true);
        }
    }

    private void changeIconColor(Context context, int drawable, int i){
        Drawable unwrappedDrawable = AppCompatResources.getDrawable(context, drawable);
        assert unwrappedDrawable != null;
        Drawable wrappedDrawable = DrawableCompat.wrap(unwrappedDrawable);
        DrawableCompat.setTint(wrappedDrawable, getResources().getColor(R.color.colorAccent));
        if(i == 0 ){
            ivHome.setImageResource(drawable);
        }else  if( i == 1){
           ivHeart.setImageResource(drawable);
        }else  if(i ==2){
            ivCart.setImageResource(drawable);
        }else  if( i == 3){
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


        homeBookFragment = new HomeBookFragment();
        orderListBookFragment = new OrderListBookFragment();
        profileAdminFragment = new ProfileAdminFragment();
        navigationView = findViewById(R.id.nav_view);
        menu = findViewById(R.id.menu);
        ll_personal_info = findViewById(R.id.ll_personal_info);
        ll_address = findViewById(R.id.ll_address);
        ll_payment = findViewById(R.id.ll_payment);
        language = findViewById(R.id.language);
        country = findViewById(R.id.country);
        profileFragment = new ProfileFragment();
        categoryListFragment = new CategoryListFragment();
        bookListFragment = new BookListFragment();
        bookstoresFragmentWithFilter = new BookstoresFragmentWithFilter();
    }


    public void editProfile(View v){
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
        if(position == 1){
            bookstoresFragment = new BookstoresFragment();
            BookStoreMainActivity.this.getSupportFragmentManager().beginTransaction()
                    .replace(R.id.content_view, bookstoresFragment)
                    .addToBackStack(null)
                    .commit();
        }
        if(position ==2 ){
            BookStoreMainActivity.this.getSupportFragmentManager().beginTransaction()
                    .replace(R.id.content_view, categoryListFragment)
                    .addToBackStack(null)
                    .commit();
        }
        if(position ==3 ){
            BookStoreMainActivity.this.getSupportFragmentManager().beginTransaction()
                    .replace(R.id.content_view, bookListFragment)
                    .addToBackStack(null)
                    .commit();
        }
        if(position ==4 ){
            BookStoreMainActivity.this.getSupportFragmentManager().beginTransaction()
                    .replace(R.id.content_view, bookstoresFragmentWithFilter)
                    .addToBackStack(null)
                    .commit();
        }
    }

    @Override
    public void onBackPressed() {
        if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }
        else {
            super.onBackPressed();
        }

    }
}

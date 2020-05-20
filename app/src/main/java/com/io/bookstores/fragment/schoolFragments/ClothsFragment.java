package com.io.bookstores.fragment.schoolFragments;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.io.bookstores.Config;
import com.io.bookstores.StaticData;
import com.io.bookstores.adapter.schoolAdapter.ClassSubCategoryRvAdapter;
import com.io.bookstores.adapter.schoolAdapter.ClothRvAdapter;
import com.io.bookstores.adapter.schoolAdapter.StudentBooksRvAdapter;
import com.io.bookstores.apicaller.ApiCaller;
import com.io.bookstores.listeners.ItemClickListner;
import com.io.bookstores.listeners.ItemClickListnerss;
import com.io.bookstores.listeners.RecyclerViewClickListenerS;
import com.io.bookstores.localStorage.LocalStorage;
import com.io.bookstores.model.loginModel.LoginModel;
import com.io.bookstores.model.productModel.ProductDataModel;
import com.io.bookstores.model.productModel.ProductResponseModel;
import com.io.bookstores.model.staticModel.ClothModel;
import com.io.bookstores.model.subCategory.ClassSubCategoriesItem;
import com.io.bookstores.model.subCategory.ClassSubCategorsResponseModel;
import com.io.bookstores.utility.NewProgressBar;
import com.io.bookstores.utility.Utils;
import com.io.bookstores.utility.userOnlineInfo;
import com.koushikdutta.async.future.FutureCallback;

import java.util.ArrayList;
import java.util.List;

import static com.io.bookstores.R.drawable;
import static com.io.bookstores.R.id;
import static com.io.bookstores.R.layout;


public class ClothsFragment extends Fragment implements View.OnClickListener, RecyclerViewClickListenerS {


    private Activity activity;
    private RecyclerView recyclerView, rv_subcategory;
    private LocalStorage localStorage;
    private NewProgressBar dialog;
    private ImageView iv_back;
    private userOnlineInfo user;
    private SearchView search_all_items;
    private LinearLayout ll_books_layout, ll_cloth_layout;
    private RecyclerView rv_books, rv_cloths;
    private ClothRvAdapter adapter;
    private LoginModel loginModel;
    private ItemClickListnerss itemClickListnerss;
    String size;
    private List<ClassSubCategoriesItem> dataItem = new ArrayList<>();
    private ClassSubCategoryRvAdapter classSubCategoryRvAdapter;
    private StudentBooksRvAdapter studentBooksRvAdapter;
    private List<ClothModel> listCloth;
    private List<ClothModel> listStudentBooks;
    private String token = "";
    private TextView tv_no_data_found_cloth, notdata;
    private ItemClickListner itemClickListner;
    private List<ProductDataModel> listData = new ArrayList<>();
    private RecyclerViewClickListenerS recyclerViewClickListener;

    public ClothsFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(layout.fragment_cloths, container, false);
        intializeViews(view);
        bindAllViews();
        startWorking();
        return view;
    }

    /*----------------------------------------- intialize all views that are used in this fragment ----------------------------*/

    private void intializeViews(View view) {
        activity = getActivity();
        listCloth = new ArrayList<>();
        recyclerViewClickListener = this;
        listStudentBooks = new ArrayList<>();
        localStorage = new LocalStorage(activity);
        user = new userOnlineInfo();
        loginModel = localStorage.getUserProfile();
        dialog = new NewProgressBar(activity);
        itemClickListner = (ItemClickListner) activity;

        iv_back = view.findViewById(id.iv_back);
        notdata = view.findViewById(id.notdata);
        ll_books_layout = view.findViewById(id.ll_books_layout);
        ll_cloth_layout = view.findViewById(id.ll_cloth_layout);
        rv_books = view.findViewById(id.rv_books);
        rv_cloths = view.findViewById(id.rv_cloths);
        tv_no_data_found_cloth = view.findViewById(id.tv_no_data_found_cloth);
        rv_subcategory = view.findViewById(id.rv_subcategory);
        search_all_items = view.findViewById(id.search_all_items);
    }

    /*-------------------------------------------- bind all view that are used in this fragment ------------------------------*/

    private void bindAllViews() {
        iv_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case id.iv_back:
                itemClickListner.onClick(6);
        }
    }

    /*-------------------------------------------------- start Working ------------------------------------------------------*/

    private void startWorking() {
        token = localStorage.getString(LocalStorage.token);
        classSubcategoryApiCall();
        setUpofUi();

    }


    /*---------------------------------------------- set of ui cloths or book ui -----------------------------------------*/

    private void setUpofUi() {
        if (StaticData.type == 1) {
            ll_cloth_layout.setVisibility(View.VISIBLE);
            ll_books_layout.setVisibility(View.GONE);
            setUpOfCloths();
        } else {
            ll_cloth_layout.setVisibility(View.GONE);
            ll_books_layout.setVisibility(View.VISIBLE);
            setUpOfStudentBooks();
            getSchoolProducts();
            searchViewSetUp();
        }
    }



    /*-------------------------------------------------class sub category api call ----------------------------------------*/

    private void classSubcategoryApiCall() {
        if (user.isOnline(activity)) {
            dialog.show();
            ApiCaller.getclassSubCategoryApi(getActivity(), Config.Url.getClassSubCategory + localStorage.getString(LocalStorage.classCategoryId),
                    new FutureCallback<ClassSubCategorsResponseModel>() {

                        @Override
                        public void onCompleted(Exception e, ClassSubCategorsResponseModel result) {
                            if (e != null) {
                                dialog.dismiss();
                                Utils.showAlertDialog(getActivity(), "Something Went Wrong");
                                return;
                            }
                            if (result != null) {
                                if (result.isStatus()) {
                                    dialog.dismiss();
                                    setUpOfSubCategory(result.getData().getClassSubCategories());
                                }
                            }else {
                                Utils.showAlertDialog(getActivity(), "Something Went Wrong");
                                dialog.dismiss();
                            }
                        }
                    });
        } else {
            Utils.showAlertDialog(getActivity(), "No Internet Connection");
        }
    }

    /*---------------------------------------- all class SubCategory Set On Recycler View -----------------------------------*/

    private void setUpOfSubCategory(List<ClassSubCategoriesItem> classSubCategories) {
        if (classSubCategories.isEmpty()) {

            rv_subcategory.setVisibility(View.GONE);
        } else {
            rv_subcategory.setVisibility(View.VISIBLE);
            rv_subcategory.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false));
            classSubCategoryRvAdapter = new ClassSubCategoryRvAdapter(activity, classSubCategories, recyclerViewClickListener);
            dataItem = classSubCategories;
            size = classSubCategories.get(0).getName();
            rv_subcategory.setAdapter(classSubCategoryRvAdapter);
            clothApiCall(size);
        }
    }

    /*------------------------------------------------- get SchoolProducts ApiCall ----------------------------------------- */

    private void getSchoolProducts() {
        if (user.isOnline(activity)) {
            ApiCaller.getSchoolProducts(getActivity(), Config.Url.schoolProducts + "/" + localStorage.getString(LocalStorage.schoolId) + "/" + localStorage.getString(LocalStorage.classGroupId) + "/" + localStorage.getString(LocalStorage.classId) + "/" + localStorage.getString(LocalStorage.classCategoryId) + "/" + null, token,
                    new FutureCallback<ProductResponseModel>() {

                        @Override
                        public void onCompleted(Exception e, ProductResponseModel result) {
                            if (e != null) {
                                Utils.showAlertDialog(getActivity(), "Something Went Wrong");
                                return;
                            }
                            if (result != null) {
                                if (result.isStatus()) {
                                    setUpOfProducts(result.getData());
                                } else {
                                    if (result.getMessage().equals("Unauthorized")) {
                                        Utils.showAlertDialogLogout(activity, "Your Session was expire. please Logout!", localStorage.getUserProfile().getData().getUser().getUserId());
                                    } else {
                                        Toast.makeText(activity, result.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }else {
                                dialog.dismiss();
                                Utils.showAlertDialog(getActivity(), "Something Went Wrong");
                            }
                        }
                    });
        } else {
            Utils.showAlertDialog(getActivity(), "No Internet Connection");
        }
    }

    private void setUpOfProducts(List<ProductDataModel> data) {
        if (data.isEmpty()) {
            notdata.setVisibility(View.VISIBLE);
            rv_books.setVisibility(View.GONE);

        } else {
            notdata.setVisibility(View.GONE);
            rv_books.setVisibility(View.VISIBLE);
            rv_books.setLayoutManager(new GridLayoutManager(activity, 3));
            studentBooksRvAdapter = new StudentBooksRvAdapter(activity, data);
            listData = data;
            rv_books.setAdapter(studentBooksRvAdapter);
        }
    }



    /*----------------------------------------------- set up of cloth recycler view --------------------------------------*/

    private void setUpOfCloths() {
        listStudentBooks.clear();
        listCloth.clear();
        listCloth.add(new ClothModel(drawable.c1, "2 KD"));
        listCloth.add(new ClothModel(drawable.c2, "2 KD"));
        listCloth.add(new ClothModel(drawable.c3, "2 KD"));
        listCloth.add(new ClothModel(drawable.c3, "2 KD"));
        listCloth.add(new ClothModel(drawable.c1, "2 KD"));
        listCloth.add(new ClothModel(drawable.c2, "2 KD"));
        /*rv_cloths.setLayoutManager(new GridLayoutManager(activity, 3));
        adapter = new ClothRvAdapter(activity, listCloth);
        rv_cloths.setAdapter(adapter);*/
    }

    /*------------------------------------------ set Up of student books recycler view -----------------------------------*/

    private void setUpOfStudentBooks() {
        listCloth.clear();
        listStudentBooks.clear();
        listStudentBooks.add(new ClothModel(drawable.b1, "2KD"));
        listStudentBooks.add(new ClothModel(drawable.b2, "2KD"));
        listStudentBooks.add(new ClothModel(drawable.b2, "2KD"));
        listStudentBooks.add(new ClothModel(drawable.b1, "2KD"));
        listStudentBooks.add(new ClothModel(drawable.b2, "2KD"));
        listStudentBooks.add(new ClothModel(drawable.b1, "2KD"));
       /* rv_books.setLayoutManager(new GridLayoutManager(activity, 3));
        studentBooksRvAdapter = new StudentBooksRvAdapter(activity, listStudentBooks);
        rv_books.setAdapter(studentBooksRvAdapter);*/
    }
    /*---------------------------------------------------- set up of search view --------------------------------------------------*/

    private void searchViewSetUp() {
        search_all_items.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if (listData == null || listData.isEmpty()) {
                    // Toast.makeText(activity, "Data Not Found", Toast.LENGTH_SHORT).show();
                } else {
                    List<ProductDataModel> newlist = new ArrayList<>();
                    for (ProductDataModel productList : listData) {
                        String name = productList.getName().toLowerCase();
                        if (name.contains(s))
                            newlist.add(productList);
                    }
                    studentBooksRvAdapter.setFilter(newlist);

                }
                return true;
            }

        });

    }

    @Override
    public void onClickPosition(String position) {
        clothApiCall(position);
    }

    private void clothApiCall(String position) {
        if (user.isOnline(activity)) {
            dialog.show();
            ApiCaller.getSchoolProducts(getActivity(), Config.Url.schoolProducts + "/" + localStorage.getString(LocalStorage.schoolId) + "/" + localStorage.getString(LocalStorage.classGroupId) + "/" + localStorage.getString(LocalStorage.classId) + "/" + localStorage.getString(LocalStorage.classCategoryId) + "/" + position, token,
                    new FutureCallback<ProductResponseModel>() {

                        @Override
                        public void onCompleted(Exception e, ProductResponseModel result) {
                            if (e != null) {
                                dialog.dismiss();
                                Utils.showAlertDialog(getActivity(), "Something Went Wrong");
                            }
                            if (result != null) {
                                if (result.isStatus()) {
                                    dialog.dismiss();
                                    setUpOfProductsCloth(result.getData());
                                } else {
                                    if (result.getMessage().equals("Unauthorized")) {
                                        Utils.showAlertDialogLogout(activity, "Your Session was expire. please Logout!", localStorage.getUserProfile().getData().getUser().getUserId());
                                        dialog.dismiss();
                                    } else {
                                        dialog.dismiss();
                                        Toast.makeText(activity, result.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            } else {
                                dialog.dismiss();
                                Utils.showAlertDialog(getActivity(), "Something Went Wrong");
                            }
                        }
                    });
        } else {
            Utils.showAlertDialog(getActivity(), "No Internet Connection");
        }
    }

    private void setUpOfProductsCloth(List<ProductDataModel> data) {
        if (data.isEmpty()) {
            rv_cloths.setVisibility(View.GONE);
            tv_no_data_found_cloth.setVisibility(View.VISIBLE);
        } else {
            rv_cloths.setVisibility(View.VISIBLE);
            tv_no_data_found_cloth.setVisibility(View.GONE);
            rv_cloths.setLayoutManager(new GridLayoutManager(activity, 3));
            adapter = new ClothRvAdapter(activity, data);
            localStorage.putString(LocalStorage.SIZEEID, "");
            localStorage.putString(LocalStorage.PQUANTITY, "");
            //listData = data;
            rv_cloths.setAdapter(adapter);
        }
    }
}

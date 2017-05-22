package com.yk.fourteen.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.OptionsPickerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.makeramen.roundedimageview.RoundedImageView;
import com.yancy.imageselector.ImageConfig;
import com.yancy.imageselector.ImageSelector;
import com.yancy.imageselector.ImageSelectorActivity;
import com.yk.fourteen.R;
import com.yk.fourteen.bean.Account;
import com.yk.fourteen.common.Common;
import com.yk.fourteen.common.PreferencesManager;
import com.yk.fourteen.utils.GlideLoader;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;

public class PerfectInfoActivity extends AppCompatActivity {


    @BindView(R.id.riv_updateInfo)
    RoundedImageView rivUpdateInfo;
    @BindView(R.id.layout_photo)
    RelativeLayout layoutPhoto;
    @BindView(R.id.sex)
    TextView sex;
    @BindView(R.id.tv_sex)
    TextView tvSex;
    @BindView(R.id.layout_sex)
    LinearLayout layoutSex;
    @BindView(R.id.age)
    TextView age;
    @BindView(R.id.tv_age)
    TextView tvAge;
    @BindView(R.id.layout_age)
    LinearLayout layoutAge;
    @BindView(R.id.address)
    TextView address;
    @BindView(R.id.layout_address)
    LinearLayout layoutAddress;
    @BindView(R.id.btn_submit)
    Button btnSubmit;
    @BindView(R.id.spin_province)
    Spinner spinProvince;
    @BindView(R.id.spin_city)
    Spinner spinCity;
    @BindView(R.id.spin_county)
    Spinner spinCounty;
    @BindView(R.id.tv_allContent)
    TextView tvAllContent;
    private ArrayList<String> ageItems = new ArrayList<>();
    private ArrayList<String> sexItems = new ArrayList<>();

    private String PhotoUrl, Sex, Age, Address;
    private Spinner provinceSpinner = null;  //省级（省、直辖市）
    private Spinner citySpinner = null;     //地级市
    private Spinner countySpinner = null;    //县级（区、县、县级市）
    ArrayAdapter<String> provinceAdapter = null;  //省级适配器
    ArrayAdapter<String> cityAdapter = null;    //地级适配器
    ArrayAdapter<String> countyAdapter = null;    //县级适配器
    static int provincePosition = 3;
    //省级选项值
    private String[] province = new String[]{"北京", "上海", "天津", "广东", "河南"};//,"重庆","黑龙江","江苏","山东","浙江","香港","澳门"};
    //地级选项值
    private String[][] city = new String[][]
            {
                    {"东城区", "西城区", "崇文区", "宣武区", "朝阳区", "海淀区", "丰台区", "石景山区", "门头沟区",
                            "房山区", "通州区", "顺义区", "大兴区", "昌平区", "平谷区", "怀柔区", "密云县",
                            "延庆县"},
                    {"长宁区", "静安区", "普陀区", "闸北区", "虹口区"},
                    {"和平区", "河东区", "河西区", "南开区", "河北区", "红桥区", "塘沽区", "汉沽区", "大港区",
                            "东丽区"},
                    {"广州", "深圳", "韶关" // ,"珠海","汕头","佛山","湛江","肇庆","江门","茂名","惠州","梅州",
                            // "汕尾","河源","阳江","清远","东莞","中山","潮州","揭阳","云浮"
                    },
                    {"郑州", "洛阳", "开封", "驻马店"
                    }
            };

    //县级选项值
    private String[][][] county = new String[][][]
            {
                    {   //北京
                            {"无"}, {"无"}, {"无"}, {"无"}, {"无"}, {"无"}, {"无"}, {"无"}, {"无"}, {"无"},
                            {"无"}, {"无"}, {"无"}, {"无"}, {"无"}, {"无"}, {"无"}, {"无"}
                    },
                    {    //上海
                            {"无"}, {"无"}, {"无"}, {"无"}, {"无"}
                    },
                    {    //天津
                            {"无"}, {"无"}, {"无"}, {"无"}, {"无"}, {"无"}, {"无"}, {"无"}, {"无"}, {"无"}
                    },
                    {    //广东
                            {"海珠区", "荔湾区", "越秀区", "白云区", "萝岗区", "天河区", "黄埔区", "花都区", "从化市", "增城市", "番禺区", "南沙区"}, //广州
                            {"宝安区", "福田区", "龙岗区", "罗湖区", "南山区", "盐田区"}, //深圳
                            {"武江区", "浈江区", "曲江区", "乐昌市", "南雄市", "始兴县", "仁化县", "翁源县", "新丰县", "乳源县"}  //韶关
                    },
                    {//河南
                            {"二七", "中原", "金水", "新郑", "新密"},
                            {"西工", "洛龙", "嵩县", "老城区", "涧西区", "吉利区"},
                            {"金明区", "龙亭区", "鼓楼区", "禹王台区", "杞县"},
                            {"新蔡县", "上蔡县", "泌阳县", "西平县", "遂平县"}, {}, {}, {}
                    }
            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfect_info);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.layout_photo, R.id.layout_sex, R.id.layout_age, R.id.layout_address, R.id.btn_submit, R.id.tv_allContent})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.layout_photo:
                perfectInfo();
                break;
            case R.id.layout_sex:
                showSex();
                break;
            case R.id.layout_age:
                showAge();
                break;
            case R.id.layout_address:
                showAddress();
                break;
            case R.id.btn_submit:
                submitUserInfo();
            case R.id.tv_allContent:
                showAllAddress();
                break;
        }
    }

    private void showAllAddress() {
        String province = spinProvince.getSelectedItem().toString();
        String city = spinCity.getSelectedItem().toString();
        String county = spinCounty.getSelectedItem().toString();
        tvAllContent.setText(province + city + county);
    }

    /**
     * 更新前需要先登录
     */
    private void submitUserInfo() {
        Sex = tvSex.getText().toString();
        Age = tvAge.getText().toString();
        Address = address.getText().toString();
        if (!TextUtils.isEmpty(PhotoUrl)
                && !TextUtils.isEmpty(Sex)
                && !TextUtils.isEmpty(Age)
                && !TextUtils.isEmpty(Address)) {
            Account newUser = new Account();
            newUser.setPhoto(PhotoUrl);
            newUser.setSex("男".equals(Sex) ? true : false);
            newUser.setAge(Age);
            newUser.setAddress(Address);
            Account bmobUser = BmobUser.getCurrentUser(PerfectInfoActivity.this, Account.class);
            newUser.update(PerfectInfoActivity.this, bmobUser.getObjectId(), new UpdateListener() {
                @Override
                public void onSuccess() {
                    // TODO Auto-generated method stub
                    Toast.makeText(PerfectInfoActivity.this, R.string.update_userinfo_success, Toast.LENGTH_SHORT).show();
                    PreferencesManager.getInstance(PerfectInfoActivity.this).put(Common.USER_PHOTO, PhotoUrl);
                    PerfectInfoActivity.this.finish();
                }

                @Override
                public void onFailure(int code, String msg) {
                    // TODO Auto-generated method stub

                    Toast.makeText(PerfectInfoActivity.this, R.string.update_userinfo_failed + msg, Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(PerfectInfoActivity.this, R.string.checkinfo, Toast.LENGTH_SHORT).show();
        }
    }

    private void showAddress() {
        //绑定适配器和值
        provinceAdapter = new ArrayAdapter<String>(PerfectInfoActivity.this,
                android.R.layout.simple_spinner_item, province);
        spinProvince.setAdapter(provinceAdapter);
        spinProvince.setSelection(3, true);  //设置默认选中项，此处为默认选中第4个值

        cityAdapter = new ArrayAdapter<String>(PerfectInfoActivity.this,
                android.R.layout.simple_spinner_item, city[3]);
        spinCity.setAdapter(cityAdapter);
        spinCity.setSelection(0, true);  //默认选中第0个

        countyAdapter = new ArrayAdapter<String>(PerfectInfoActivity.this,
                android.R.layout.simple_spinner_item, county[3][0]);
        spinCounty.setAdapter(countyAdapter);
        spinCounty.setSelection(0, true);
        //省级下拉框监听
        spinProvince.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            // 表示选项被改变的时候触发此方法，主要实现办法：动态改变地级适配器的绑定值
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long arg3) {
                //position为当前省级选中的值的序号

                //将地级适配器的值改变为city[position]中的值
                cityAdapter = new ArrayAdapter<String>(
                        PerfectInfoActivity.this, android.R.layout.simple_spinner_item, city[position]);
                // 设置二级下拉列表的选项内容适配器
                spinCity.setAdapter(cityAdapter);
                provincePosition = position;    //记录当前省级序号，留给下面修改县级适配器时用
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }

        });
        //地级下拉监听
        spinCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int position, long arg3) {
                countyAdapter = new ArrayAdapter<String>(PerfectInfoActivity.this,
                        android.R.layout.simple_spinner_item, county[provincePosition][position]);
                spinCounty.setAdapter(countyAdapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });
    }

    //年龄
    private void showAge() {
        for (int i = 1; i < 100; i++) {
            ageItems.add(String.valueOf(i));
        }
        OptionsPickerView pvOptions = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                tvAge.setText(ageItems.get(options1));
            }
        })
                .setTitleText(getString(R.string.select_age))
                .setDividerColor(Color.BLACK)
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setContentTextSize(20)
                .setOutSideCancelable(false)// default is true
                .build();
        pvOptions.setPicker(ageItems);//一级选择器
        pvOptions.show();

    }

    //性别
    private void showSex() {
        sexItems.add("男");
        sexItems.add("女");
        OptionsPickerView pvOptions = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                tvSex.setText(sexItems.get(options1));
            }
        })
                .setTitleText(getString(R.string.select_sex))
                .setDividerColor(Color.BLACK)
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setContentTextSize(20)
                .setOutSideCancelable(false)// default is true
                .build();
        pvOptions.setPicker(sexItems);//一级选择器
        pvOptions.show();

    }

    //完善信息
    private void perfectInfo() {
        ImageConfig imageConfig
                = new ImageConfig.Builder(new GlideLoader())
                .steepToolBarColor(getResources().getColor(R.color.colorPrimary))
                .titleBgColor(getResources().getColor(R.color.colorPrimary))
                .titleSubmitTextColor(getResources().getColor(R.color.white))
                .titleTextColor(getResources().getColor(R.color.white))
                // (截图默认配置：关闭    比例 1：1    输出分辨率  500*500)
                .crop(1, 1, 300, 300)
                // 开启单选   （默认为多选）
                .singleSelect()
                // 开启拍照功能 （默认关闭）
                .showCamera()
                // 拍照后存放的图片路径（默认 /temp/picture） （会自动创建）
                .filePath("/six/Pictures")
                .build();
        ImageSelector.open(PerfectInfoActivity.this, imageConfig);   // 开启图片选择器
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ImageSelector.IMAGE_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            // Get Image Path List
            List<String> pathList = data.getStringArrayListExtra(ImageSelectorActivity.EXTRA_RESULT);
            if (pathList.size() > 0) {
                //由于单选只需要回去第一个数据就好,获取图片URL并上传
                uploadPhotoForURL(pathList.get(0));
            } else {
                Toast.makeText(PerfectInfoActivity.this,R.string.select_pic_failed,Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void uploadPhotoForURL(String s) {
        final BmobFile bmobFile = new BmobFile(new File(s));
        bmobFile.uploadblock(PerfectInfoActivity.this, new UploadFileListener() {

            @Override
            public void onSuccess() {
                PhotoUrl = bmobFile.getFileUrl(PerfectInfoActivity.this);
                Glide.get(rivUpdateInfo.getContext()).with(rivUpdateInfo.getContext())
                        .load(PhotoUrl)
                        .asBitmap()//强制转换Bitmap
                        .placeholder(R.mipmap.login_photo)
                        .error(R.mipmap.aio_image_fail_round)
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .into(rivUpdateInfo);
                Toast.makeText(PerfectInfoActivity.this,R.string.upload_pic_success + PhotoUrl,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onProgress(Integer value) {
            }

            @Override
            public void onFailure(int code, String msg) {
                Toast.makeText(PerfectInfoActivity.this,R.string.upload_pic_failure + msg,Toast.LENGTH_SHORT).show();
            }
        });
    }
}

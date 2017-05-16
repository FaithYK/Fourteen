package com.yk.fourteen.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by YAY on 2017/5/15.
 */

public class Joke implements Parcelable {


    /**
     * showapi_res_code : 0
     * showapi_res_error :
     * showapi_res_body : {"allNum":933,"allPages":47,"contentlist":[{"ct":"　2015-07-10 05:54:00.000","text":"　　男生拉着女生沮丧着脸说，再给我一次机会，求求你！说好不提分手的！<br />\r\n　　女生甩开男孩的手说：你TM现在在我心里就是个菩萨，除了拜拜我什么都不想做。","title":"你TM现在在我心里就是个菩萨"}],"currentPage":1,"maxResult":20}
     */

    private int showapi_res_code;
    private String showapi_res_error;
    private ShowapiResBodyBean showapi_res_body;

    public int getShowapi_res_code() {
        return showapi_res_code;
    }

    public void setShowapi_res_code(int showapi_res_code) {
        this.showapi_res_code = showapi_res_code;
    }

    public String getShowapi_res_error() {
        return showapi_res_error;
    }

    public void setShowapi_res_error(String showapi_res_error) {
        this.showapi_res_error = showapi_res_error;
    }

    public ShowapiResBodyBean getShowapi_res_body() {
        return showapi_res_body;
    }

    public void setShowapi_res_body(ShowapiResBodyBean showapi_res_body) {
        this.showapi_res_body = showapi_res_body;
    }

    public static class ShowapiResBodyBean implements Parcelable {
        /**
         * allNum : 933
         * allPages : 47
         * contentlist : [{"ct":"　2015-07-10 05:54:00.000","text":"　　男生拉着女生沮丧着脸说，再给我一次机会，求求你！说好不提分手的！<br />\r\n　　女生甩开男孩的手说：你TM现在在我心里就是个菩萨，除了拜拜我什么都不想做。","title":"你TM现在在我心里就是个菩萨"}]
         * currentPage : 1
         * maxResult : 20
         */

        private int allNum;
        private int allPages;
        private int currentPage;
        private int maxResult;
        private List<ContentlistBean> contentlist;

        public int getAllNum() {
            return allNum;
        }

        public void setAllNum(int allNum) {
            this.allNum = allNum;
        }

        public int getAllPages() {
            return allPages;
        }

        public void setAllPages(int allPages) {
            this.allPages = allPages;
        }

        public int getCurrentPage() {
            return currentPage;
        }

        public void setCurrentPage(int currentPage) {
            this.currentPage = currentPage;
        }

        public int getMaxResult() {
            return maxResult;
        }

        public void setMaxResult(int maxResult) {
            this.maxResult = maxResult;
        }

        public List<ContentlistBean> getContentlist() {
            return contentlist;
        }

        public void setContentlist(List<ContentlistBean> contentlist) {
            this.contentlist = contentlist;
        }

        public static class ContentlistBean implements Parcelable {
            /**
             * ct : 　2015-07-10 05:54:00.000
             * text : 　　男生拉着女生沮丧着脸说，再给我一次机会，求求你！说好不提分手的！<br />
             　　女生甩开男孩的手说：你TM现在在我心里就是个菩萨，除了拜拜我什么都不想做。
             * title : 你TM现在在我心里就是个菩萨
             */

            private String ct;
            private String text;
            private String title;

            public String getCt() {
                return ct;
            }

            public void setCt(String ct) {
                this.ct = ct;
            }

            public String getText() {
                return text;
            }

            public void setText(String text) {
                this.text = text;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(this.ct);
                dest.writeString(this.text);
                dest.writeString(this.title);
            }

            public ContentlistBean() {
            }

            protected ContentlistBean(Parcel in) {
                this.ct = in.readString();
                this.text = in.readString();
                this.title = in.readString();
            }

            public static final Creator<ContentlistBean> CREATOR = new Creator<ContentlistBean>() {
                @Override
                public ContentlistBean createFromParcel(Parcel source) {
                    return new ContentlistBean(source);
                }

                @Override
                public ContentlistBean[] newArray(int size) {
                    return new ContentlistBean[size];
                }
            };
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.allNum);
            dest.writeInt(this.allPages);
            dest.writeInt(this.currentPage);
            dest.writeInt(this.maxResult);
            dest.writeList(this.contentlist);
        }

        public ShowapiResBodyBean() {
        }

        protected ShowapiResBodyBean(Parcel in) {
            this.allNum = in.readInt();
            this.allPages = in.readInt();
            this.currentPage = in.readInt();
            this.maxResult = in.readInt();
            this.contentlist = new ArrayList<ContentlistBean>();
            in.readList(this.contentlist, ContentlistBean.class.getClassLoader());
        }

        public static final Creator<ShowapiResBodyBean> CREATOR = new Creator<ShowapiResBodyBean>() {
            @Override
            public ShowapiResBodyBean createFromParcel(Parcel source) {
                return new ShowapiResBodyBean(source);
            }

            @Override
            public ShowapiResBodyBean[] newArray(int size) {
                return new ShowapiResBodyBean[size];
            }
        };
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.showapi_res_code);
        dest.writeString(this.showapi_res_error);
        dest.writeParcelable(this.showapi_res_body, flags);
    }

    public Joke() {
    }

    protected Joke(Parcel in) {
        this.showapi_res_code = in.readInt();
        this.showapi_res_error = in.readString();
        this.showapi_res_body = in.readParcelable(ShowapiResBodyBean.class.getClassLoader());
    }

    public static final Parcelable.Creator<Joke> CREATOR = new Parcelable.Creator<Joke>() {
        @Override
        public Joke createFromParcel(Parcel source) {
            return new Joke(source);
        }

        @Override
        public Joke[] newArray(int size) {
            return new Joke[size];
        }
    };
}

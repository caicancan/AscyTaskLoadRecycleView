package com.ffpy.demo;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by pythonCat on 2016/6/1.
 */
public class Beauty implements Parcelable {

    String name ;
    int age ;
    String sex ;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * 将对象序列号
     * dest 就是对象即将写入的目的对象
     * flags 有关对象序列号的方式的标识
     * 这里要注意，写入的顺序要和在createFromParcel方法中读出的顺序完全相同。例如这里先写入的为name，
     * 那么在createFromParcel就要先读name
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(name);
        dest.writeInt(age);
        dest.writeString(sex);
    }
    /**
     * 在想要进行序列号传递的实体类内部一定要声明该常量。常量名只能是CREATOR,类型也必须是
     * Parcelable.Creator<T>
     */
    public static final Creator<Beauty> CREATOR = new Creator<Beauty>() {

        /**
         * 创建一个要序列号的实体类的数组，数组中存储的都设置为null
         */
        @Override
        public Beauty[] newArray(int size) {
            return new Beauty[size];
        }

        /***
         * 根据序列号的Parcel对象，反序列号为原本的实体对象
         * 读出顺序要和writeToParcel的写入顺序相同
         */
        @Override
        public Beauty createFromParcel(Parcel source) {
            String name = source.readString();
            int age = source.readInt();
            String sex = source.readString();
            Beauty beauty = new Beauty();
            beauty.setName(name);
            beauty.setAge(age);
            beauty.setSex(sex);

            return beauty;
        }
    };



}
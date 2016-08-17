# VerticalTabLayout

![image](https://github.com/kim-wang-bj/VerticalTabLayout/raw/master/images/screenshot_1.png)

###Example

```xml
   <com.wq.android.lib.verticaltablayout.VerticalTabLayout
        android:id="@+id/tab_layout"
        android:layout_width="150dp"
        android:layout_height="match_parent"
        android:background="#cccccc"
        app:dividerColor="@android:color/holo_blue_dark"
        app:dividerHeight="1px"
        app:dividerPadding="15dp"
        app:tabArrowColor="@android:color/holo_orange_dark"
        app:tabArrowGravity="right"
        app:tabArrowType="outer"
        app:tabDrawablePadding="8dp"
        app:tabHeight="30dp"
        app:tabIndicatorColor="@android:color/holo_orange_dark"
        app:tabIndicatorGravity="fill"
        app:tabIndicatorWidth="15dp"
        app:tabPaddingLeft="20dp"
        app:tabSelectedTextColor="@android:color/white"
        app:tabTextColor="#555555"
        app:tabTextSize="10dp"
        app:tabViewGravity="left"/>
```
```java
VerticalTabLayout vTabLayout = (VerticalTabLayout) findViewById(R.id.tab_layout);
vTabLayout.addTab(vTabLayout.newTab().setText("TEST").setDrawable(R.drawable.ic_selector));
vTabLayout.setOnTabSelectedListener(new VerticalTabLayout.OnTabSelectedAdapter() {
   @Override
   public void onTabSelected(VerticalTabLayout.Tab tab, int position) {
       Toast.makeText(getApplicationContext(), "onTabSelected: " + position, Toast.LENGTH_SHORT).show();
   }
});
```

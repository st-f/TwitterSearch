package net.stf.twittersearch;

import android.test.ActivityInstrumentationTestCase2;
import android.test.UiThreadTest;
import android.view.View;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;

import java.util.ArrayList;

/**
 * Created by st-f on 07/07/2016
 */

public class MainActivityTest extends ActivityInstrumentationTestCase2<MainActivity> {

    private static final String TEST_TEXT = "test0";
    private static final String TEST_IMAGE = "test1";
    private static final String TEST_PROFILE_NAME = "test2";
    private static final String TEST_PROFILE_IMAGE = "test3";
    private ListAdapter mAdapter;

    public MainActivityTest() {
        super(MainActivity.class);
    }

    protected void setUp() throws Exception {
        super.setUp();
        ArrayList<ListAdapter.ListViewItemModel> data = new ArrayList<>();
        ListAdapter.ListViewItemModel itemModel = new ListAdapter.ListViewItemModel();
        itemModel.profileImageURL = TEST_PROFILE_IMAGE;
        itemModel.profileName = TEST_PROFILE_NAME;
        itemModel.text = TEST_TEXT;
        itemModel.imageURL = TEST_IMAGE;
        data.add(itemModel);
        mAdapter = new ListAdapter(getInstrumentation().getTargetContext(), R.layout.list_item, data);
    }

    /**
     * This method gets the first view from the adapter and retrieve the image's tag.
     * The tag is used for images as there is no getter for imageURL in NetworkImageView.
     */
    @UiThreadTest
    public void testGetView() {
        View view = mAdapter.getView(0, null, null);
        TextView textView = (TextView) view.findViewById(R.id.list_item_text);
        NetworkImageView imageView = (NetworkImageView) view.findViewById(R.id.list_item_imageview);
        NetworkImageView profileImageView = (NetworkImageView) view.findViewById(R.id.list_item_profile_imageview);
        TextView profileTextView = (TextView) view.findViewById(R.id.list_item_profile_name_tv);
        assertNotNull("View is null. ", view);
        assertNotNull("Profile ImageView is null. ", profileImageView);
        assertNotNull("Profile name TextView is null. ", profileTextView);
        assertNotNull("TextView is null. ", textView);
        assertNotNull("ImageView is null. ", imageView);
        assertEquals("Profile image URL doesn't match.", TEST_PROFILE_IMAGE, profileImageView.getTag());
        assertEquals("Profile text doesn't match.", TEST_PROFILE_NAME, profileTextView.getText());
        assertEquals("Text doesn't match.", TEST_TEXT, textView.getText());
        assertEquals("Image URL doesn't match.", TEST_IMAGE, imageView.getTag());
    }
}
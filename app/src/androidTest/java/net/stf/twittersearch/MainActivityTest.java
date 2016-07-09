package net.stf.twittersearch;

import android.test.ActivityInstrumentationTestCase2;
import android.test.UiThreadTest;
import android.view.View;
import android.widget.RelativeLayout;
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
        ListAdapter.ListViewItemModel tweetWithImage = new ListAdapter.ListViewItemModel();
        tweetWithImage.profileImageURL = TEST_PROFILE_IMAGE;
        tweetWithImage.profileName = TEST_PROFILE_NAME;
        tweetWithImage.text = TEST_TEXT;
        tweetWithImage.imageURL = TEST_IMAGE;
        ListAdapter.ListViewItemModel tweetWithoutImage = new ListAdapter.ListViewItemModel();
        tweetWithoutImage .profileImageURL = TEST_PROFILE_IMAGE;
        tweetWithoutImage .profileName = TEST_PROFILE_NAME;
        tweetWithoutImage .text = TEST_TEXT;
        data.add(tweetWithImage);
        data.add(tweetWithoutImage);
        mAdapter = new ListAdapter(getInstrumentation().getTargetContext(), R.layout.list_item, data);
    }

    /**
     * This method gets the first view from the adapter and retrieve the image's tag.
     * The tag is used for images as there is no getter for imageURL in NetworkImageView.
     */
    @UiThreadTest
    public void testGetViewWithImage() {
        View view = mAdapter.getView(0, null, null);
        testView(true, view);
    }
    
    @UiThreadTest
    public void testGetViewWithoutImage() {
        View view = mAdapter.getView(1, null, null);
        testView(false, view);
    }

    private void testView(boolean withImage, View view) {
        TextView textView = (TextView) view.findViewById(R.id.list_item_text);
        NetworkImageView imageView = (NetworkImageView) view.findViewById(R.id.list_item_imageview);
        NetworkImageView profileImageView = (NetworkImageView) view.findViewById(R.id.list_item_profile_imageview);
        TextView profileTextView = (TextView) view.findViewById(R.id.list_item_profile_name_tv);
        RelativeLayout imageContainer = (RelativeLayout) view.findViewById(R.id.list_item_image_container);
        assertNotNull("View is not null.", view);
        assertNotNull("Profile ImageView is not null.", profileImageView);
        assertEquals("Profile text doesn't match.", TEST_PROFILE_NAME, profileTextView.getText());
        assertEquals("Text doesn't match.", TEST_TEXT, textView.getText());
        assertNotNull("Profile name TextView is not null.", profileTextView);
        assertNotNull("TextView is not null.", textView);
        if(withImage) {
            assertNotNull("ImageView is not null.", imageView);
            assertEquals("Image URL doesn't match.", TEST_IMAGE, imageView.getTag());
            assertEquals("Profile image URL doesn't match.", TEST_PROFILE_IMAGE, profileImageView.getTag());
        } else {
            assertEquals("ImageView is invisible.", false, (imageContainer.getVisibility() == View.VISIBLE) ? true : false);
        }
    }
}
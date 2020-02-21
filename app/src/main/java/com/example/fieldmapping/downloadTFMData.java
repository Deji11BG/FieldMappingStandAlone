package com.example.fieldmapping;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

import androidx.annotation.NonNull;
import androidx.room.Room;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class downloadTFMData {
    private downloadMembersModel membersModel;
    private Context context;
    private ApiInterface apiInterface;
    private TFMDatabase tfmDatabase;

    private List<downloadMembersModel> membersList= new ArrayList<>();

    public downloadTFMData(Context context) {
        membersModel = new downloadMembersModel();
        this.context = context;
    }
    private void getInputRecords(final String staff_id) {
        //String last_synced = "0";

        SharedPreferenceController sharedPreferenceController = new SharedPreferenceController(context);
        String last_synced = sharedPreferenceController.getDateUpdated();
        ApiInterface service = ApiClient.getInstance().create(ApiInterface.class);
        Call<List<downloadMembersModel>> call = service.getInputRecords(staff_id,last_synced);
        //Call<List<downloadMembersModel>> call = service.getInputRecords();


        call.enqueue(new Callback<List<downloadMembersModel>>() {
            @Override
            public void onResponse(@NonNull Call<List<downloadMembersModel>> call,
                                   @NonNull Response<List<downloadMembersModel>> response) {
//                Log.d("tobiRes", ""+ Objects.requireNonNull(response.body()).toString());
                if (response.isSuccessful()) {
                    membersList = response.body();

                    Log.d("Retrofit_response1", Objects.requireNonNull(membersList).toString());
                    String memberSize = String.valueOf(membersList != null ? membersList.size() : 0);
                    Log.d("listSize", memberSize);

                    if (membersList == null){
                        String last_sync_time_input_records = membersList.get(membersList.size()-1).getDate_updated();

                        //sharedPreferenceController.setDateUpdated("1","Input records download null",sharedPreferenceController.getTfmInputSyncTime());
                    }else if(membersList.size() == 0){
                        //sharedPreferenceController.setTFMInputFlagsAndDescriptions("1","Input records download empty",sharedPreferenceController.getTfmInputSyncTime());
                    }else {
                        //String last_sync_time_input_records = oldMembersDownloadModelList.get(0).getLast_sync_down_time_input_records();
                        String last_sync_time_input_records = membersList.get(membersList.size()-1).getDate_updated();

                        sharedPreferenceController.setDateUpdated(last_sync_time_input_records);

                        Log.d("last_sync",last_sync_time_input_records+"");

                        for (int i = 0; i < membersList.size(); i++) {
                            downloadMembersModel member = membersList.get(i);
                            saveToOldMembersTable(member);
                            saveOutputRecordPictures(member);

                        }
                    }


                }else {
                    int sc = response.code();
                    Log.d("scCode_input_records",""+sc);
                    switch (sc) {
                        case 400:
                            Log.e("Error 400", "Bad Request");
                            //sharedPreferenceController.setTFMInputFlagsAndDescriptions("0","Error 400: Input records download failed",sharedPreferenceController.getTfmOutputSyncTime());
                            break;
                        case 404:
                            Log.e("Error 404", "Not Found");
                            //sharedPreferenceController.setTFMInputFlagsAndDescriptions("0","Error 404: Input records download failed",sharedPreferenceController.getTfmOutputSyncTime());
                            break;
                        default:
                            Log.e("Error", "Generic Error");
                            //sharedPreferenceController.setTFMInputFlagsAndDescriptions("0","Generic error: Input records download failed",sharedPreferenceController.getTfmOutputSyncTime());
                            break;
                    }
                }

            }

            @Override
            public void onFailure(@NotNull Call<List<downloadMembersModel>> call, @NotNull Throwable t) {
                Log.d("tobi_input_records", t.toString());

                //sharedPreferenceController.setTFMInputFlagsAndDescriptions("0","Input records download failed",sharedPreferenceController.getTfmOutputSyncTime());

            }
        });

    }
    private void saveToOldMembersTable(downloadMembersModel member){
         MembersTable MembersListData = new MembersTable();
        MembersListData.setUnique_member_id(member.getUnique_member_id());
        MembersListData.setUnique_ik_number(member.getUnique_ik_number());
        MembersListData.setIk_number(member.getIk_number());
        MembersListData.setMember_id(member.getMember_id());
        MembersListData.setFirst_name(member.getFirst_name());
        MembersListData.setMiddle_name(member.getMiddle_name());
        MembersListData.setLast_name(member.getLast_name());
        MembersListData.setPhone_number(member.getPhone_number());
        MembersListData.setVillage_name(member.getVillage_name());
        MembersListData.setRole(member.getRole());
        MembersListData.setTemplate(member.getTemplate());
        MembersListData.setWard_id(member.getWard_id());
        MembersListData.setLoan_field_size(member.getInput_field_size());

        SaveTFMInputData task = new SaveTFMInputData();
        task.execute(MembersListData);


//        if(ImageStorage.checkIfImageExists(member.getPicture_name(),"TestPictures",context)) {
//            GetImages getImages = new GetImages(member.getPicture_url(),member.getPicture_name(),"TestPictures",context,"test_pic");
//            Log.d("logger","getting_here");
//            getImages.execute();
//        }

    }
    @SuppressLint("StaticFieldLeak")
    public class SaveTFMInputData extends AsyncTask<MembersTable, Void, Void> {


        private final String TAG = SaveTFMInputData.class.getSimpleName();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(MembersTable... params) {

            MembersTable membersTable = params[0];

            try {
                tfmDatabase = TFMDatabase.getInstance(context);
                tfmDatabase.getMembersTable().insert(membersTable);
            } catch (Exception e) {
                Log.d(TAG, Objects.requireNonNull(e.getMessage()));
            }

            return null;
        }
    }
    public void syncData() {
        //SharedPreference sharedPreference = new SharedPreference(context);
        //HashMap<String, String> user = sharedPreference.getUserDetails();
        //String staff_id = user.get(SharedPreference.KEY_STAFF_ID);

        getInputRecords("T10001");

    }
    private class GetImages extends AsyncTask<Object, Object, Object> {
        private String requestUrl, imageName, folder_name;
        //private ImageView view;
        private Bitmap bitmap ;
        private FileOutputStream fos;
        Context mCtx;
        String module;

        private GetImages(String requestUrl, String image_name, String folder_name, Context context, String module) {
            this.requestUrl = requestUrl;
            //this.view = view;
            this.imageName = image_name ;
            this.folder_name = folder_name ;
            this.mCtx = context;
            this.module = module;
        }

        @Override
        protected Object doInBackground(Object... objects) {
            try {

                Log.d("logger","getting_here");

                HttpURLConnection connection = null;
                InputStream is = null;
                ByteArrayOutputStream out = null;
                try {
                    connection = (HttpURLConnection) new URL(requestUrl).openConnection();
                    is = connection.getInputStream();
                    bitmap = BitmapFactory.decodeStream(is);
                } catch (Throwable e) {
                    if (!this.isCancelled()) {
                        //error = new ImageError(e).setErrorCode(ImageError.ERROR_GENERAL_EXCEPTION);
                        this.cancel(true);
                    }
                } finally {
                    try {
                        if (connection != null)
                            connection.disconnect();
                        if (out != null) {
                            out.flush();
                            out.close();
                        }
                        if (is != null)
                            is.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            if(ImageStorage.checkIfImageExists(imageName,folder_name,mCtx)) {
                //view.setImageBitmap(bitmap);

                String result = ImageStorage.saveToSdCard(bitmap, imageName,folder_name,mCtx,module);
                Log.d("storeResult",result+"");
                if (result == null){
                    Log.d("image_store","Null result");
                }else if (result.equalsIgnoreCase("fileExists")){
                    Log.d("image_store","Image did not save");
                }else if (result.equalsIgnoreCase("success")){
                    Log.d("image_store","Image saved");
                }
            }
        }
    }
    public static class ImageStorage {

        @SuppressWarnings("ResultOfMethodCallIgnored")
        static String saveToSdCard(Bitmap bitmap, String filename, String folder_name, Context context, String module) {

            String stored = null;

            File ChkImgDirectory;
            String storageState = Environment.getExternalStorageState();
            if (storageState.equals(Environment.MEDIA_MOUNTED)) {
                if (module.equalsIgnoreCase("tfm_pic")){
                    ChkImgDirectory = new File(Objects.requireNonNull(context.getExternalFilesDir(null)).getAbsoluteFile(), folder_name);
                }else{
                    ChkImgDirectory = new File(Environment.getExternalStorageDirectory().getAbsoluteFile(), folder_name);
                }

                File file, file3;
                File file1 = new File(ChkImgDirectory.getAbsoluteFile(), filename + ".jpg");
                File file2 = new File(ChkImgDirectory.getAbsoluteFile(), ".nomedia");
                if (!ChkImgDirectory.exists() && !ChkImgDirectory.mkdirs()) {
                    ChkImgDirectory.mkdir();
                    file = file1;
                    file3 = file2;
                    if (!file3.exists()){
                        try {
                            FileOutputStream outNoMedia = new FileOutputStream(file3);
                            outNoMedia.flush();
                            outNoMedia.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (file.exists()){
                        stored = "fileExists";
                    }else{
                        try {
                            FileOutputStream out = new FileOutputStream(file);
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 20, out);
                            out.flush();
                            out.close();
                            stored = "success";
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                } else {
                    file = file1;
                    file3 = file2;
                    if (!file3.exists()){
                        try {
                            FileOutputStream outNoMedia = new FileOutputStream(file3);
                            outNoMedia.flush();
                            outNoMedia.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (file.exists()){
                        stored = "fileExists";
                    }else{
                        try {
                            FileOutputStream out = new FileOutputStream(file);
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 20, out);
                            out.flush();
                            out.close();
                            stored = "success";
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            return stored;
        }

        static File getImage(String imageName, String folder_name, Context context) {

            File mediaImage = null;
            try {
                String root = Objects.requireNonNull(context.getExternalFilesDir(null)).getAbsoluteFile().toString();
                File myDir = new File(root);
                if (!myDir.exists())
                    return null;

                mediaImage = new File(myDir.getPath() + folder_name + imageName);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return mediaImage;
        }

        static boolean checkIfImageExists(String imageName, String folder_name, Context context) {
            Bitmap b;
            File file = ImageStorage.getImage("/"+imageName+".jpg","/"+folder_name,context);
            String path = Objects.requireNonNull(file).getAbsolutePath();
            b = BitmapFactory.decodeFile(path);

            return b != null;
        }
    }

    private void saveOutputRecordPictures(downloadMembersModel membersTable){
        if(ImageStorage.checkIfImageExists(membersTable.getUnique_member_id(), TFMDBContractClass.SMALL_FM_PICTURE_LOCATION,context)) {
            GetImages getImages = new GetImages(ApiClient.BASE_URL+"tfm_pictures/"+membersTable.getUnique_member_id()+".jpg",
                    membersTable.getUnique_member_id(),TFMDBContractClass.SMALL_FM_PICTURE_LOCATION,context,"tfm_pic");
            getImages.execute();
        }
    }

}

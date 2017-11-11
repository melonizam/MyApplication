package com.notes.tyrocity.myapplication;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    private static final String TAG = "MainActivity";
    // Folder path for Firebase Storage.
    String Storage_Path = "All_Image_Uploads/";

    String lll = "Australia - Sydney.txt Austria.txt Belarus.txt Belgium.txt Brazil.txt Bulgaria.txt Canada-East.txt Canada-Ontario.txt Canada-West.txt Chile.txt Croatia.txt Cyprus.txt Czech Republic.txt Denmark.txt Estonia .txt Finland.txt France2.txt France .txt Germany2.txt Germany.txt Greece.txt Hong Kong.txt Hungary.txt Iceland.txt India1.txt India2.txt Ireland.txt Isle of Man.txt Israel.txt Italy.txt Japan.txt Latvia.txt Lithuania.txt Luxembourg.txt Malaysia.txt Mexico.txt Moldova.txt Netherlands.txt New Zealand.txt Norway.txt Poland.txt Portugal.txt Romania.txt Singapore .txt Slovenia.txt South Africa.txt South Korea2.txt South Korea.txt Spain.txt Sweden.txt Switzerland.txt Thailand.txt Turkey.txt Ukraine.txt United Kingdom2.txt United Kingdom.txt US-California2.txt US-California.txt US-Florida.txt US-Illinois.txt US-Missouri.txt US-Nevada.txt US-New York.txt US-Streaming.txt US-Texas1.txt US-Utah.txt US-Virginia.txt US-Washington.txt";

    // Root Database Name for Firebase Database.
    public static final String Database_Path = "All_Image_Uploads_Database";

    // Creating button.
    Button ChooseButton, UploadButton, DisplayImageButton;

    // Creating EditText.
    EditText ImageName ;

    // Creating ImageView.
    ImageView SelectImage;

    // Creating URI.
    Uri FilePathUri;

    // Creating StorageReference and DatabaseReference object.
    StorageReference storageReference;
    DatabaseReference databaseReference;

    // Image request code for onActivityResult() .
    int Image_Request_Code = 7;

    ProgressDialog progressDialog ;

    protected boolean shouldAskPermissions() {
        return (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1);
    }

    @TargetApi(23)
    protected void askPermissions() {
        String[] permissions = {
                "android.permission.READ_EXTERNAL_STORAGE",
                "android.permission.WRITE_EXTERNAL_STORAGE"
        };
        int requestCode = 200;
        requestPermissions(permissions, requestCode);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (shouldAskPermissions()) {
            askPermissions();
        }


        String ab = lll.replaceAll(".txt ",".txt\", \"" );
        Log.d(TAG, "onCreate: " +ab);

        String ll =lll.replaceAll(".txt", ",");
        Log.d(TAG, "onCreate: " + ll);
        String l = ll.replaceAll(", ", ", http://www.sciencekids.co.nz/images/pictures/flags680/");
        Log.d(TAG, "onCreate: 2nd" + l);
        String last = l.replaceAll(",", ".jpg,");
        Log.d(TAG, "onCreate: 2nd" + last);

        String bb = last.replaceAll(", ", "\",\"");
        Log.d(TAG, "onCreate: " + bb);

        String cc = l.replaceAll(", ", "\",\"");
        Log.d(TAG, "onCreate: " + cc);



        String[] text = new String[]{ "Australia - Sydney.txt", "Austria.txt", "Belarus.txt", "Belgium.txt", "Brazil.txt", "Bulgaria.txt", "Canada-East.txt", "Canada-Ontario.txt", "Canada-West.txt", "Chile.txt", "Croatia.txt", "Cyprus.txt", "Czech Republic.txt", "Denmark.txt", "Estonia .txt", "Finland.txt", "France2.txt", "France .txt", "Germany2.txt", "Germany.txt", "Greece.txt", "Hong Kong.txt", "Hungary.txt", "Iceland.txt", "India1.txt", "India2.txt", "Ireland.txt", "Isle of Man.txt", "Israel.txt", "Italy.txt", "Japan.txt", "Latvia.txt", "Lithuania.txt", "Luxembourg.txt", "Malaysia.txt", "Mexico.txt", "Moldova.txt", "Netherlands.txt", "New Zealand.txt", "Norway.txt", "Poland.txt", "Portugal.txt", "Romania.txt", "Singapore .txt", "Slovenia.txt", "South Africa.txt", "South Korea2.txt", "South Korea.txt", "Spain.txt", "Sweden.txt", "Switzerland.txt", "Thailand.txt", "Turkey.txt", "Ukraine.txt", "United Kingdom2.txt", "United Kingdom.txt", "US-California2.txt", "US-California.txt", "US-Florida.txt", "US-Illinois.txt", "US-Missouri.txt", "US-Nevada.txt", "US-New York.txt", "US-Streaming.txt", "US-Texas1.txt", "US-Utah.txt", "US-Virginia.txt", "US-Washington.txt"};

        String[] tt = new String[]{"Australia - Sydney","Austria","Belarus","Belgium","Brazil","Bulgaria","Canada-East","Canada-Ontario","Canada-West","Chile","Croatia","Cyprus","Czech Republic","Denmark","Estonia ","Finland","France2","France ","Germany2","Germany","Greece","Hong Kong","Hungary","Iceland","India1","India2","Ireland","Isle of Man","Israel","Italy","Japan","Latvia","Lithuania","Luxembourg","Malaysia","Mexico","Moldova","Netherlands","New Zealand","Norway","Poland","Portugal","Romania","Singapore ","Slovenia","South Africa","South Korea2","South Korea","Spain","Sweden","Switzerland","Thailand","Turkey","Ukraine","United Kingdom2","United Kingdom","US-California2","US-California","US-Florida","US-Illinois","US-Missouri","US-Nevada","US-New York","US-Streaming","US-Texas1","US-Utah","US-Virginia","US-Washington"};

        String[] url = new String[]{"http://www.sciencekids.co.nz/images/pictures/flags680/Australia - Sydney.jpg","http://www.sciencekids.co.nz/images/pictures/flags680/Austria.jpg","http://www.sciencekids.co.nz/images/pictures/flags680/Belarus.jpg","http://www.sciencekids.co.nz/images/pictures/flags680/Belgium.jpg","http://www.sciencekids.co.nz/images/pictures/flags680/Brazil.jpg","http://www.sciencekids.co.nz/images/pictures/flags680/Bulgaria.jpg","http://www.sciencekids.co.nz/images/pictures/flags680/Canada-East.jpg","http://www.sciencekids.co.nz/images/pictures/flags680/Canada-Ontario.jpg","http://www.sciencekids.co.nz/images/pictures/flags680/Canada-West.jpg","http://www.sciencekids.co.nz/images/pictures/flags680/Chile.jpg","http://www.sciencekids.co.nz/images/pictures/flags680/Croatia.jpg","http://www.sciencekids.co.nz/images/pictures/flags680/Cyprus.jpg","http://www.sciencekids.co.nz/images/pictures/flags680/Czech Republic.jpg","http://www.sciencekids.co.nz/images/pictures/flags680/Denmark.jpg","http://www.sciencekids.co.nz/images/pictures/flags680/Estonia .jpg","http://www.sciencekids.co.nz/images/pictures/flags680/Finland.jpg","http://www.sciencekids.co.nz/images/pictures/flags680/France2.jpg","http://www.sciencekids.co.nz/images/pictures/flags680/France .jpg","http://www.sciencekids.co.nz/images/pictures/flags680/Germany2.jpg","http://www.sciencekids.co.nz/images/pictures/flags680/Germany.jpg","http://www.sciencekids.co.nz/images/pictures/flags680/Greece.jpg","http://www.sciencekids.co.nz/images/pictures/flags680/Hong Kong.jpg","http://www.sciencekids.co.nz/images/pictures/flags680/Hungary.jpg","http://www.sciencekids.co.nz/images/pictures/flags680/Iceland.jpg","http://www.sciencekids.co.nz/images/pictures/flags680/India1.jpg","http://www.sciencekids.co.nz/images/pictures/flags680/India2.jpg","http://www.sciencekids.co.nz/images/pictures/flags680/Ireland.jpg","http://www.sciencekids.co.nz/images/pictures/flags680/Isle of Man.jpg","http://www.sciencekids.co.nz/images/pictures/flags680/Israel.jpg","http://www.sciencekids.co.nz/images/pictures/flags680/Italy.jpg","http://www.sciencekids.co.nz/images/pictures/flags680/Japan.jpg","http://www.sciencekids.co.nz/images/pictures/flags680/Latvia.jpg","http://www.sciencekids.co.nz/images/pictures/flags680/Lithuania.jpg","http://www.sciencekids.co.nz/images/pictures/flags680/Luxembourg.jpg","http://www.sciencekids.co.nz/images/pictures/flags680/Malaysia.jpg","http://www.sciencekids.co.nz/images/pictures/flags680/Mexico.jpg","http://www.sciencekids.co.nz/images/pictures/flags680/Moldova.jpg","http://www.sciencekids.co.nz/images/pictures/flags680/Netherlands.jpg","http://www.sciencekids.co.nz/images/pictures/flags680/New Zealand.jpg","http://www.sciencekids.co.nz/images/pictures/flags680/Norway.jpg","http://www.sciencekids.co.nz/images/pictures/flags680/Poland.jpg","http://www.sciencekids.co.nz/images/pictures/flags680/Portugal.jpg","http://www.sciencekids.co.nz/images/pictures/flags680/Romania.jpg","http://www.sciencekids.co.nz/images/pictures/flags680/Singapore .jpg","http://www.sciencekids.co.nz/images/pictures/flags680/Slovenia.jpg","http://www.sciencekids.co.nz/images/pictures/flags680/South Africa.jpg","http://www.sciencekids.co.nz/images/pictures/flags680/South Korea2.jpg","http://www.sciencekids.co.nz/images/pictures/flags680/South Korea.jpg","http://www.sciencekids.co.nz/images/pictures/flags680/Spain.jpg","http://www.sciencekids.co.nz/images/pictures/flags680/Sweden.jpg","http://www.sciencekids.co.nz/images/pictures/flags680/Switzerland.jpg","http://www.sciencekids.co.nz/images/pictures/flags680/Thailand.jpg","http://www.sciencekids.co.nz/images/pictures/flags680/Turkey.jpg","http://www.sciencekids.co.nz/images/pictures/flags680/Ukraine.jpg","http://www.sciencekids.co.nz/images/pictures/flags680/United Kingdom2.jpg","http://www.sciencekids.co.nz/images/pictures/flags680/United Kingdom.jpg","http://www.sciencekids.co.nz/images/pictures/flags680/US-California2.jpg","http://www.sciencekids.co.nz/images/pictures/flags680/US-California.jpg","http://www.sciencekids.co.nz/images/pictures/flags680/US-Florida.jpg"};
        ArrayList<ImageUploadInfo> model = new ArrayList<>();
        Log.d(TAG, "onCreate: " +String.valueOf(tt.length)+ " "+ String.valueOf(url.length));

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String name = preferences.getString("Name", "");
        if(!name.equalsIgnoreCase(""))
        {
            name = name + "  Sethi";  /* Edit the value here*/
            Toast.makeText(this, name, Toast.LENGTH_SHORT).show();
        }


//        for (int i = 0; i<(url.length); i++){
//            model.add(new ImageUploadInfo(tt[i], url[i]));
//        }
//
//        for (int j=0; j < model.size(); j++){
//           // Log.d(TAG, "onCreate:Model " + model.get(j).getCountry() + model.get(j).getUrl().replaceAll(" ", ""));
//        }
//        // Assign FirebaseStorage instance to storageReference.
        storageReference = FirebaseStorage.getInstance().getReference();

        // Assign FirebaseDatabase instance with root database name.
        databaseReference = FirebaseDatabase.getInstance().getReference(Database_Path);

        //mFirebaseDatabase = FirebaseDatabase.getInstance().getReference("users");

//        User user = new User(emailNameText.getText().toString());
//        for (int i = 1; i<(url.length); i++){
////            model.add(new Model(tt[i], url[i]));
//            String userId = databaseReference.push().getKey();
//            databaseReference.child(userId).setValue(new ImageUploadInfo(tt[i], url[i]));
//        }


        //Assign ID'S to button.
        ChooseButton = (Button)findViewById(R.id.ButtonChooseImage);
        UploadButton = (Button)findViewById(R.id.ButtonUploadImage);

        DisplayImageButton = (Button)findViewById(R.id.DisplayImagesButton);

        // Assign ID's to EditText.
        ImageName = (EditText)findViewById(R.id.ImageNameEditText);

        // Assign ID'S to image view.
        SelectImage = (ImageView)findViewById(R.id.ShowImageView);

        // Assigning Id to ProgressDialog.
        progressDialog = new ProgressDialog(MainActivity.this);

        // Adding click listener to Choose image button.
        ChooseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Creating intent.
                Intent intent = new Intent();

                // Setting intent type as image to select image from phone storage.
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Please Select Image"), Image_Request_Code);

            }
        });


        // Adding click listener to Upload image button.
        UploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Calling method to upload selected image on Firebase storage.
                UploadImageFileToFirebaseStorage();

            }
        });


        DisplayImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, DisplayImagesActivity.class);
                startActivity(intent);

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Image_Request_Code && resultCode == RESULT_OK && data != null && data.getData() != null) {

            FilePathUri = data.getData();

            try {

                // Getting selected image into Bitmap.
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), FilePathUri);

                // Setting up bitmap selected image into ImageView.
                SelectImage.setImageBitmap(bitmap);

                // After selecting image change choose button above text.
                ChooseButton.setText("Image Selected");

            }
            catch (IOException e) {

                e.printStackTrace();
            }
        }
    }

    // Creating Method to get the selected image file Extension from File Path URI.
    public String GetFileExtension(Uri uri) {

        ContentResolver contentResolver = getContentResolver();

        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();

        // Returning the file Extension.
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri)) ;

    }

    // Creating UploadImageFileToFirebaseStorage method to upload image on storage.
    public void UploadImageFileToFirebaseStorage() {

        // Checking whether FilePathUri Is empty or not.
        if (FilePathUri != null) {

            // Setting progressDialog Title.
            progressDialog.setTitle("Image is Uploading...");

            // Showing progressDialog.
            progressDialog.show();

            // Creating second StorageReference.
            StorageReference storageReference2nd = storageReference.child(Storage_Path + System.currentTimeMillis() + "." + GetFileExtension(FilePathUri));

            // Adding addOnSuccessListener to second StorageReference.
            storageReference2nd.putFile(FilePathUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            // Getting image name from EditText and store into string variable.
                            String TempImageName = ImageName.getText().toString().trim();

                            // Hiding the progressDialog after done uploading.
                            progressDialog.dismiss();

                            // Showing toast message after done uploading.
                            Toast.makeText(getApplicationContext(), "Image Uploaded Successfully ", Toast.LENGTH_LONG).show();

                            @SuppressWarnings("VisibleForTests")
                            ImageUploadInfo imageUploadInfo = new ImageUploadInfo(TempImageName, taskSnapshot.getDownloadUrl().toString());

                            // Getting image upload ID.
                            String ImageUploadId = databaseReference.push().getKey();

                            // Adding image upload id s child element into databaseReference.
                            databaseReference.child(ImageUploadId).setValue(imageUploadInfo);
                        }
                    })
                    // If something goes wrong .
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {

                            // Hiding the progressDialog.
                            progressDialog.dismiss();

                            // Showing exception erro message.
                            Toast.makeText(MainActivity.this, exception.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    })

                    // On progress change upload time.
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {

                            // Setting progressDialog Title.
                            progressDialog.setTitle("Image is Uploading...");

                        }
                    });
        }
        else {

            Toast.makeText(MainActivity.this, "Please Select Image or Add Image Name", Toast.LENGTH_LONG).show();

        }
    }


}


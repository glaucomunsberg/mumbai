package plataformaparaformal.mumbai;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Display;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import plataformaparaformal.mumbai.services.Mumbai;


public class Edit extends Fragment {
    private static View view;

    private ArrayList mSelectedItems;
    private AlertDialog.Builder builder;
    private Mumbai mumbai = Mumbai.getInstance();
    private static final int PICK_IMAGE_REQUEST=1;
    private static final int TAKE_PICTURE=2;
    private int shiftId,registeredActivityId,registeredAmountId,localizationSpaceId,positionBodyId,numberBodyId,equipmentMobilityId,equipmentScaleId;
    private ArrayList<Integer> senseId,equipmentInstalationId;
    private Uri image;
    private ImageView img;

    public View onCreateView(LayoutInflater inflater,  ViewGroup container,Bundle savedInstanceState) {

        if (view != null) {
            ViewGroup parent = (ViewGroup) view.getParent();
            if (parent != null)
                parent.removeView(view);
        }
        try {
            view = inflater.inflate(R.layout.fragment_edit, container, false);
        } catch (InflateException e) {

        }
        setHasOptionsMenu(true);


        /**
         * Get and Set information
         */
        img = (ImageView) view.findViewById(R.id.edit_img);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder1 =builder = new AlertDialog.Builder(getActivity());
                String [] posibilities = getResources().getStringArray(R.array.edit_choosePictureOrigins);
                // Set the dialog title
                builder.setTitle(R.string.menu_options)
                        // Specify the list array, the items to be selected by default (null for none),
                        // and the listener through which to receive callbacks when items are selected

                        .setItems(posibilities, new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                    if(arg1 == 0){
                                        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                                            startActivityForResult(takePictureIntent, TAKE_PICTURE);
                                        }
                                    }else{
                                        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                                            // Create the File where the photo should go
                                            File photoFile = null;
                                            try {
                                                photoFile = createImageFile();
                                                image = Uri.fromFile(photoFile);
                                            } catch (IOException ex) {
                                                // Error occurred while creating the File

                                            }
                                            // Continue only if the File was successfully created
                                            if (photoFile != null) {
                                                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                                                        Uri.fromFile(photoFile));
                                                startActivityForResult(takePictureIntent, TAKE_PICTURE);
                                            }
                                        }
                                    }
                            }
                        });
                builder.create();
                builder.show();



            }
        });

        TextView description = (TextView) view.findViewById(R.id.edit_description);


        String[] menu_itens = getResources().getStringArray(R.array.paraformalidade_search_menu);

        ListView listView;
        listView = (ListView) view.findViewById( R.id.edit_paraformalidade_menu );

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1, menu_itens);


        /**
         * Event of choose
         */
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        registeredActivitiesSelected();
                        break;
                    case 1:
                        shiftSelected();
                        break;
                    case 2:
                        registeredAmountSelected();
                        break;
                    case 3:
                        localizationSpacSelected();
                        break;
                    case 4:
                        positionBodySelected();
                        break;
                    case 5:
                        numberBodySelected();
                        break;
                    case 6:
                        senseSelected();
                        break;
                    case 7:
                        equipmentMobilitySelected();
                        break;
                    case 8:
                        equipmentScale();
                        break;
                    case 9:
                        equipmentInstalationsSelected();
                        break;
                    default:
                        mumbai.config.principalToast.setText("Error Search");
                        mumbai.config.principalToast.show();
                }
            }
        });


        return view;

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu_edit, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    public void registeredActivitiesSelected(){
        mSelectedItems = new ArrayList();  // Where we track the selected items
        builder = new AlertDialog.Builder(getActivity());
        // Set the dialog title
        builder.setTitle(R.string.menu_options)
                // Specify the list array, the items to be selected by default (null for none),
                // and the listener through which to receive callbacks when items are selected
                .setItems(mumbai.budapest.getStringFromArrayDescription(mumbai.budapest.registeredActivities), new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        // TODO Auto-generated method stub
                        // mSelectedItems.add(0, mumbai.budapest.getStringFromArrayDescription(mumbai.budapest.registeredActivities)[arg1].toString());
                        //mumbai.config.principalToast.setText(mumbai.budapest.getStringFromArrayDescription(mumbai.budapest.registeredActivities)[arg1].toString());
                        //mumbai.config.principalToast.show();
                        registeredActivityId = mumbai.budapest.getIdFromDescription(mumbai.budapest.registeredActivities, mumbai.budapest.getStringFromArrayDescription(mumbai.budapest.registeredActivities)[arg1].toString());
                        mumbai.config.principalToast.show();
                    }
                })
                        // Set the action buttons
                .setPositiveButton(R.string.menu_ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // User clicked OK, so save the mSelectedItems results somewhere
                        // or return them to the component that opened the dialog

                    }
                })
                .setNegativeButton(R.string.menu_cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // do samting
                    }
                });
        builder.create();
        builder.show();
    }

    public void shiftSelected(){
        mSelectedItems = new ArrayList();  // Where we track the selected items
        CharSequence[] tipos =  mumbai.budapest.getStringFromArrayDescription(mumbai.budapest.shifts);
        builder = new AlertDialog.Builder(getActivity());
        // Set the dialog title
        builder.setTitle(R.string.menu_options)
                // Specify the list array, the items to be selected by default (null for none),
                // and the listener through which to receive callbacks when items are selected


                .setItems(mumbai.budapest.getStringFromArrayDescription(mumbai.budapest.shifts), new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        // TODO Auto-generated method stub

                        shiftId = mumbai.budapest.getIdFromDescription(mumbai.budapest.shifts,mumbai.budapest.getStringFromArrayDescription(mumbai.budapest.shifts)[arg1].toString());
                    }
                })
                        // Set the action buttons
                .setPositiveButton(R.string.menu_ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // User clicked OK, so save the mSelectedItems results somewhere
                        // or return them to the component that opened the dialog

                    }
                })
                .setNegativeButton(R.string.menu_cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // do samting
                    }
                });
        builder.create();
        builder.show();
    }

    public void registeredAmountSelected(){
        mSelectedItems = new ArrayList();  // Where we track the selected items
        builder = new AlertDialog.Builder(getActivity());
        // Set the dialog title
        builder.setTitle(R.string.menu_options)
                // Specify the list array, the items to be selected by default (null for none),
                // and the listener through which to receive callbacks when items are selected
                .setItems(mumbai.budapest.getStringFromArrayDescription(mumbai.budapest.registeredAmounts), new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        registeredAmountId = mumbai.budapest.getIdFromDescription(mumbai.budapest.registeredAmounts, mumbai.budapest.getStringFromArrayDescription(mumbai.budapest.registeredAmounts)[arg1].toString());
                    }
                })
                        // Set the action buttons
                .setPositiveButton(R.string.menu_ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // User clicked OK, so save the mSelectedItems results somewhere
                        // or return them to the component that opened the dialog

                    }
                })
                .setNegativeButton(R.string.menu_cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // do samting
                    }
                });
        builder.create();
        builder.show();
    }

    public void localizationSpacSelected(){
        mSelectedItems = new ArrayList();  // Where we track the selected items
        builder = new AlertDialog.Builder(getActivity());
        // Set the dialog title
        builder.setTitle(R.string.menu_options)
                // Specify the list array, the items to be selected by default (null for none),
                // and the listener through which to receive callbacks when items are selected
                .setItems(mumbai.budapest.getStringFromArrayDescription(mumbai.budapest.localizationSpaces), new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        localizationSpaceId = mumbai.budapest.getIdFromDescription(mumbai.budapest.localizationSpaces, mumbai.budapest.getStringFromArrayDescription(mumbai.budapest.localizationSpaces)[arg1].toString());
                    }
                })
                        // Set the action buttons
                .setPositiveButton(R.string.menu_ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // User clicked OK, so save the mSelectedItems results somewhere
                        // or return them to the component that opened the dialog

                    }
                })
                .setNegativeButton(R.string.menu_cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // do samting
                    }
                });
        builder.create();
        builder.show();
    }

    public void positionBodySelected(){
        mSelectedItems = new ArrayList();  // Where we track the selected items
        builder = new AlertDialog.Builder(getActivity());
        // Set the dialog title
        builder.setTitle(R.string.menu_options)
                // Specify the list array, the items to be selected by default (null for none),
                // and the listener through which to receive callbacks when items are selected
                .setItems(mumbai.budapest.getStringFromArrayDescription(mumbai.budapest.positionBodies), new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        positionBodyId = mumbai.budapest.getIdFromDescription(mumbai.budapest.positionBodies, mumbai.budapest.getStringFromArrayDescription(mumbai.budapest.positionBodies)[arg1].toString());
                    }
                })
                        // Set the action buttons
                .setPositiveButton(R.string.menu_ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // User clicked OK, so save the mSelectedItems results somewhere
                        // or return them to the component that opened the dialog

                    }
                })
                .setNegativeButton(R.string.menu_cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // do samting
                    }
                });
        builder.create();
        builder.show();
    }

    public void numberBodySelected(){
        mSelectedItems = new ArrayList();  // Where we track the selected items
        builder = new AlertDialog.Builder(getActivity());
        // Set the dialog title
        builder.setTitle(R.string.menu_options)
                // Specify the list array, the items to be selected by default (null for none),
                // and the listener through which to receive callbacks when items are selected
                .setItems(mumbai.budapest.getStringFromArrayDescription(mumbai.budapest.numberBodies), new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                        numberBodyId = mumbai.budapest.getIdFromDescription(mumbai.budapest.numberBodies, mumbai.budapest.getStringFromArrayDescription(mumbai.budapest.numberBodies)[arg1].toString());
                    }
                })
                        // Set the action buttons
                .setPositiveButton(R.string.menu_ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // User clicked OK, so save the mSelectedItems results somewhere
                        // or return them to the component that opened the dialog

                    }
                })
                .setNegativeButton(R.string.menu_cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // do samting
                    }
                });
        builder.create();
        builder.show();
    }

    public void senseSelected(){
        mSelectedItems = new ArrayList();  // Where we track the selected items
        builder = new AlertDialog.Builder(getActivity());
        // Set the dialog title
        builder.setTitle(R.string.menu_options)
                // Specify the list array, the items to be selected by default (null for none),
                // and the listener through which to receive callbacks when items are selected
                .setMultiChoiceItems(mumbai.budapest.getStringFromArrayDescription(mumbai.budapest.senses), null,
                        new DialogInterface.OnMultiChoiceClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which,
                                                boolean isChecked) {
                                if (isChecked) {
                                    // If the user checked the item, add it to the selected items
                                    senseId.add(which);
                                } else if (mSelectedItems.contains(which)) {
                                    // Else, if the item is already in the array, remove it
                                    senseId.remove(Integer.valueOf(which));
                                }
                            }
                        })
                        // Set the action buttons
                .setPositiveButton(R.string.menu_ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // User clicked OK, so save the mSelectedItems results somewhere
                        // or return them to the component that opened the dialog

                    }
                })
                .setNegativeButton(R.string.menu_cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // do samting
                    }
                });
        builder.create();
        builder.show();
    }

    public void equipmentInstalationsSelected(){
        mSelectedItems = new ArrayList();  // Where we track the selected items
        builder = new AlertDialog.Builder(getActivity());
        // Set the dialog title
        builder.setTitle(R.string.menu_options)
                // Specify the list array, the items to be selected by default (null for none),
                // and the listener through which to receive callbacks when items are selected
                .setMultiChoiceItems(mumbai.budapest.getStringFromArrayDescription(mumbai.budapest.equipmentInstalations), null,
                        new DialogInterface.OnMultiChoiceClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which,
                                                boolean isChecked) {
                                if (isChecked) {
                                    // If the user checked the item, add it to the selected items
                                    equipmentInstalationId.add(which);
                                } else if (mSelectedItems.contains(which)) {
                                    // Else, if the item is already in the array, remove it
                                    equipmentInstalationId.remove(Integer.valueOf(which));
                                }
                            }
                        })
                        // Set the action buttons
                .setPositiveButton(R.string.menu_ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // User clicked OK, so save the mSelectedItems results somewhere
                        // or return them to the component that opened the dialog

                    }
                })
                .setNegativeButton(R.string.menu_cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // do samting
                    }
                });
        builder.create();
        builder.show();
    }

    public void equipmentMobilitySelected(){
        mSelectedItems = new ArrayList();  // Where we track the selected items
        builder = new AlertDialog.Builder(getActivity());
        // Set the dialog title
        builder.setTitle(R.string.menu_options)
                // Specify the list array, the items to be selected by default (null for none),
                // and the listener through which to receive callbacks when items are selected
                .setItems(mumbai.budapest.getStringFromArrayDescription(mumbai.budapest.equipmentMobilities), new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        equipmentMobilityId = mumbai.budapest.getIdFromDescription(mumbai.budapest.equipmentMobilities, mumbai.budapest.getStringFromArrayDescription(mumbai.budapest.equipmentMobilities)[arg1].toString());
                    }
                })
                        // Set the action buttons
                .setPositiveButton(R.string.menu_ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // User clicked OK, so save the mSelectedItems results somewhere
                        // or return them to the component that opened the dialog

                    }
                })
                .setNegativeButton(R.string.menu_cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // do samting
                    }
                });
        builder.create();
        builder.show();
    }

    public void equipmentScale(){
        mSelectedItems = new ArrayList();  // Where we track the selected items
        builder = new AlertDialog.Builder(getActivity());
        // Set the dialog title
        builder.setTitle(R.string.menu_options)
                // Specify the list array, the items to be selected by default (null for none),
                // and the listener through which to receive callbacks when items are selected
                .setItems(mumbai.budapest.getStringFromArrayDescription(mumbai.budapest.equipmentScale), new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        equipmentScaleId = mumbai.budapest.getIdFromDescription(mumbai.budapest.equipmentScale, mumbai.budapest.getStringFromArrayDescription(mumbai.budapest.equipmentScale)[arg1].toString());
                    }
                })
                        // Set the action buttons
                .setPositiveButton(R.string.menu_ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // User clicked OK, so save the mSelectedItems results somewhere
                        // or return them to the component that opened the dialog

                    }
                })
                .setNegativeButton(R.string.menu_cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // do samting
                    }
                });
        builder.create();
        builder.show();
    }

    /**
     * Get the result of programa that select image
     * @param aRequestCode
     * @param aResultCode
     * @param aData
     */
    public void onActivityResult( int aRequestCode, int aResultCode, Intent aData) {
        switch (aRequestCode) {
            case PICK_IMAGE_REQUEST:
                if ((aData != null) && (aData.getData() != null)) {
                    Uri _imageUri = aData.getData();
                    mumbai.config.principalToast.setText(_imageUri.getPath());
                    mumbai.config.principalToast.show();
                    Display display = getActivity().getWindowManager().getDefaultDisplay();
                    Point size = new Point();
                    display.getSize(size);
                    int width = img.getWidth();
                    int height = img.getHeight();
                    img.setImageURI(_imageUri);
                    img.getLayoutParams().width = width;
                    img.getLayoutParams().height = height;

                } else {
                    mumbai.config.principalToast.setText("Sem dados errado");
                    mumbai.config.principalToast.show();
                }
                break;
            case TAKE_PICTURE:

                if (aResultCode == getActivity().RESULT_OK) {
                    Bundle extras = aData.getExtras();
                    Bitmap imageBitmap = (Bitmap) extras.get("data");
                    img.setImageBitmap(imageBitmap);

                }

                break;
            default:
                mumbai.config.principalToast.setText("arquivo errado");
                mumbai.config.principalToast.show();
                break;
        }
    }
    private File createImageFile() throws IOException {
            // Create an image file name
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String imageFileName = "JPEG_" + timeStamp + "_";
            File storageDir = Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_PICTURES);
            File image = File.createTempFile(
                    imageFileName,  /* prefix */
                    ".jpg",         /* suffix */
                    storageDir      /* directory */
            );

            // Save a file: path for use with ACTION_VIEW intents

            return image;
        }
}

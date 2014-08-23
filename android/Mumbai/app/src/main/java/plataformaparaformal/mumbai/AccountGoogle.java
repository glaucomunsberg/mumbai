package plataformaparaformal.mumbai;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.PlusClient;
import com.google.android.gms.common.GooglePlayServicesClient.ConnectionCallbacks;
import com.google.android.gms.common.GooglePlayServicesClient.OnConnectionFailedListener;

public class AccountGoogle extends Fragment implements ConnectionCallbacks,OnConnectionFailedListener {
    private PlusClient mPlusClient;

    public View onCreateView(LayoutInflater inflater,  ViewGroup container,Bundle savedInstanceState) {
        mPlusClient = new PlusClient.Builder(getActivity(), this, this)
                .build();
        return inflater.inflate(R.layout.fragment_account_google, container, false);
    }

    @Override
    public void onDisconnected() {

    }

    @Override
    public void onConnected(Bundle bundle) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

}

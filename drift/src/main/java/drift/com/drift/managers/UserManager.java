package drift.com.drift.managers;

import java.util.ArrayList;
import java.util.HashMap;

import drift.com.drift.api.APIManager;
import drift.com.drift.model.User;
import drift.com.drift.wrappers.APICallbackWrapper;
import drift.com.drift.wrappers.UserManagerCallback;
import drift.com.drift.wrappers.UserManagerWrapper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by eoin on 04/08/2017.
 */

public class UserManager {

    private static String TAG = UserManager.class.getSimpleName();

    private static UserManager _userManager = new UserManager();

    public static UserManager getInstance() {
        return _userManager;
    }

    public HashMap<Integer, User> userMap = new HashMap<>();

    public void clearCache(){
        userMap = new HashMap<>();
    }

    public void getUsers(int orgId, final UserManagerCallback userManagerCallback){

        UserManagerWrapper.getUsers(orgId, new APICallbackWrapper<ArrayList<User>>() {
            @Override
            public void onResponse(ArrayList<User> response) {
                if (response != null) {
                    userMap.clear();
                    for (User user : response) {
                        userMap.put(user.id, user);
                    }
                    userManagerCallback.didLoadUsers(true);
                } else {
                    userManagerCallback.didLoadUsers(false);
                }
            }
        });
    }

    public void getUsersIfWeNeedTo(int orgId, final UserManagerCallback userManagerCallback) {

        if (!userMap.isEmpty()) {
            userManagerCallback.didLoadUsers(true);
            return;
        }

        getUsers(orgId, userManagerCallback);
    }
}

package ph.digipay.digipayelectroniclearning.ui.common.firebase_db;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import ph.digipay.digipayelectroniclearning.models.DatabaseObject;

public class FirebaseDatabaseHelper<T> {

    private FirebaseDatabase firebaseDatabase;
    private List<T> items;

    private final Class<T> activityClass;

    public FirebaseDatabaseHelper(Class<T> activityClass) {
        firebaseDatabase = FirebaseDatabase.getInstance();
        this.activityClass = activityClass;
    }

    public void insertItems(String dataReference, DatabaseObject obj) {
        DatabaseReference databaseReference = firebaseDatabase.getReference(dataReference);
        String id = databaseReference.push().getKey();
        assert id != null;
        obj.setUid(id);
        databaseReference.child(id).setValue(obj);
    }

    public void fetchItems(String dataRef, OnGetDataListener<T> onGetDataListener) {
        items = new ArrayList<>();
        DatabaseReference databaseReference = firebaseDatabase.getReference(dataRef);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot item : dataSnapshot.getChildren()) {
                    if (item != null) {
                        items.add(item.getValue(activityClass));
                    }
                }
                onGetDataListener.onSuccess(items);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("TAG", databaseError.toException().getLocalizedMessage());
            }
        });
    }

    public void updateItem(String dataRef, String uid, String field, @Nullable String subField, String value) {
        DatabaseReference databaseReference = firebaseDatabase.getReference(dataRef);
        if (subField == null) {
            databaseReference.child(uid).child(field).setValue(value);
        } else {
            databaseReference.child(uid).child(field).child(subField).setValue(value);
        }
    }

    public void removeItem(String dataRef, String uid) {
        DatabaseReference databaseReference = firebaseDatabase.getReference(dataRef);
        databaseReference.child(uid).removeValue();
    }

    public interface OnGetDataListener<T>{
        void onSuccess(List<T> itemList);
    }
}

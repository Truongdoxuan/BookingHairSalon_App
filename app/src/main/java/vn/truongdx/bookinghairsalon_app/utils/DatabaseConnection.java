package vn.truongdx.bookinghairsalon_app.utils;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DatabaseConnection {
    //tham chiếu đến FirebaseDatabase
    public static FirebaseDatabase getDatabaseInstance() {
        return FirebaseDatabase.getInstance();
    }

    //tham chiếu đến một node
    public static DatabaseReference getDatabaseReference(String nodeName) {
        return getDatabaseInstance().getReference(nodeName);
    }
}

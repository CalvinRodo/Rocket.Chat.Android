package chat.rocket.android.fragment.chatroom;

import android.os.Bundle;
import android.support.annotation.Nullable;
import chat.rocket.android.R;
import chat.rocket.android.model.Room;
import io.realm.Realm;
import io.realm.RealmQuery;
import jp.co.crowdworks.realm_java_helpers.RealmObjectObserver;

/**
 */
public class RoomFragment extends AbstractChatRoomFragment {

  private String roomId;

  public static RoomFragment create(String roomId) {
    Bundle args = new Bundle();
    args.putString("roomId", roomId);
    RoomFragment fragment = new RoomFragment();
    fragment.setArguments(args);
    return fragment;
  }

  public RoomFragment() {
  }

  @Override public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    Bundle args = getArguments();
    roomId = args.getString("roomId");
  }

  @Override protected int getLayout() {
    return R.layout.fragment_room;
  }

  @Override protected void onSetupView() {

  }

  private RealmObjectObserver<Room> roomObserver = new RealmObjectObserver<Room>() {
    @Override protected RealmQuery<Room> query(Realm realm) {
      return realm.where(Room.class).equalTo("_id", roomId);
    }

    @Override protected void onChange(Room room) {
      onRenderRoom(room);
    }
  };

  private void onRenderRoom(Room room) {
    activityToolbar.setTitle(room.getName());
  }

  @Override public void onResume() {
    super.onResume();
    roomObserver.sub();
  }

  @Override public void onPause() {
    roomObserver.unsub();
    super.onPause();
  }
}
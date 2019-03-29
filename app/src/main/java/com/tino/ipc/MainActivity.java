package com.tino.ipc;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.tino.ipc.aidl.Book;
import com.tino.ipc.aidl.BookManagerService;
import com.tino.ipc.aidl.IBookManager;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ServiceConnection mConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName className, IBinder service) {
            Log.i("BookManager", "onServiceConnected()");
            IBookManager bookManager = IBookManager.Stub.asInterface(service);
            try {
                List<Book> list = bookManager.getBookList();
                Log.i("BookManager", "List Type:" + list.getClass().getCanonicalName());
                Log.i("BookManager", "Book List:" + list.toString());
//                Book newBook = new Book(3, "book3");
//                bookManager.addBook(newBook);
//                Log.i("BookManager", "Add Book:" + newBook);
//                List<Book> newList = bookManager.getBookList();
//                Log.i("BookManager", "Book List:" + newList.toString());
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        public void onServiceDisconnected(ComponentName className) {
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = new Intent(this, BookManagerService.class);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onDestroy() {
        unbindService(mConnection);
        super.onDestroy();
    }
}
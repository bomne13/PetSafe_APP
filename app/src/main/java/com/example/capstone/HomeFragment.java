package com.example.capstone;

import android.Manifest;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;


public class HomeFragment extends Fragment implements MachineAdapter.OnListItemSwitchInterface {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    LinearLayout manulaLayout, noAlarmLinear;
    Button manualBtn, moreviewBtn, msgBtn;
    ImageButton bellBtn, manualdelBtn, machineAddBtn;
    TextView nomachine_txt, connect_txt, reviceTxt;

    private List<MachineData> machineList;
    private MachineAdapter machineAdapter;
    private RecyclerView machine_view;

    private List<AlarmData> alarmList;
    private AlarmAdapter alarmAdapter;
    private RecyclerView alarm_view;

    static int machine_density, alarm_density;


    //블루투스 관련 변수
    private static final int REQUEST_ENABLE_BT = 10; // 블루투스 활성화 상태
    private BluetoothAdapter bluetoothAdapter; // 블루투스 어댑터
    private Set<BluetoothDevice> devices; // 블루투스 디바이스 데이터 셋
    private BluetoothDevice bluetoothDevice; // 블루투스 디바이스
    private BluetoothSocket bluetoothSocket = null; // 블루투스 소켓
    private OutputStream outputStream = null; // 블루투스에 데이터를 출력하기 위한 출력 스트림
    private InputStream inputStream = null; // 블루투스에 데이터를 입력하기 위한 입력 스트림
    private Thread workerThread = null; // 문자열 수신에 사용되는 쓰레드
    private byte[] readBuffer; // 수신 된 문자열을 저장하기 위한 버퍼
    private int readBufferPosition; // 버퍼 내 문자 저장 위치
    int pairedDeviceCount;  //페어링 된 기기 크기 저장 변수

    String[] array;
    ArrayList<String> device = new ArrayList<>();
    String[] MsgReceive;

    String userId;


    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_home, container, false);

        connect_txt = rootView.findViewById(R.id.connect_txt);
        reviceTxt = (TextView) rootView.findViewById(R.id.reviceTxt);

        if(getArguments() != null){
            userId = getArguments().getString("userId");
        }

        bellBtn = rootView.findViewById(R.id.bellBtn);
        bellBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),
                        Notification.class);
                startActivity(intent);
            }
        });

        manualdelBtn = rootView.findViewById(R.id.manualdelBtn);
        manulaLayout = rootView.findViewById(R.id.manulaLayout);

        manualdelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                manulaLayout.setVisibility(View.GONE);
            }
        });

        moreviewBtn = rootView.findViewById(R.id.moreviewBtn);
        moreviewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),
                        Notification.class);
                startActivity(intent);
            }
        });

        //recycleview 최대 높이 제한을 위한 변수
        machine_density = (int) getResources().getDisplayMetrics().density;
        alarm_density = (int) getResources().getDisplayMetrics().density;

        machine_view = rootView.findViewById(R.id.machine_view);
        nomachine_txt = rootView.findViewById(R.id.nomachine_txt);

        alarm_view = rootView.findViewById(R.id.alarm_view);
        noAlarmLinear = rootView.findViewById(R.id.noAlarmLinear);

//        alarmList = new ArrayList<>();
//        alarmList.add(new AlarmData(R.drawable.ic_imgicon_circle, "기기명1", "2022-08-07 20:45"));
//
//
//        if (alarmList.size() == 0) {
//            alarm_view.setVisibility(View.GONE);
//            noAlarmLinear.setVisibility(View.VISIBLE);
//        } else if (alarmList.size() > 3) {
//            alarm_view.setVisibility(View.VISIBLE);
//            noAlarmLinear.setVisibility(View.GONE);
//
//            ViewGroup.LayoutParams params = alarm_view.getLayoutParams();
//            params.height = 300 * alarm_density;
//            alarm_view.setLayoutParams(params);
//        } else {
//            alarm_view.setVisibility(View.VISIBLE);
//            noAlarmLinear.setVisibility(View.GONE);
//        }
//
//        alarmAdapter = new AlarmAdapter(alarmList, this);
//        alarm_view.setAdapter(alarmAdapter);
//        alarm_view.setLayoutManager(new LinearLayoutManager(getActivity()));

        machineAddBtn = rootView.findViewById(R.id.machineAddBtn);
        machineAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String[] permission_list = {
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                };

                ActivityCompat.requestPermissions(getActivity(), permission_list, 1);

                // Enable bluetooth
                bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

                if (bluetoothAdapter == null) {
                    Toast.makeText(getActivity(), "블루투스를 지원하지 않는 기기입니다.", Toast.LENGTH_SHORT).show();
                } else {
                    if (bluetoothAdapter.isEnabled()) {
                        selectBlutoothDevice();
                    } else {
                        Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                        startActivityForResult(intent, REQUEST_ENABLE_BT);
                        selectBlutoothDevice();
                    }
                }
            }
        });

        return rootView;
    }


    //페어링된 기기 목록 출력
    public void selectBlutoothDevice() {
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(getActivity(), "블루투스 사용 권한을 추가해주세요", Toast.LENGTH_SHORT).show();
            return;
        }
        devices = bluetoothAdapter.getBondedDevices();
        pairedDeviceCount = devices.size();

        if (pairedDeviceCount == 0) {
            Toast.makeText(getActivity(), "블루투스로 기기를 먼저 연결해주세요.", Toast.LENGTH_SHORT).show();
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("연결된 Device 목록");
            List<String> list = new ArrayList<>();

            for (BluetoothDevice bluetoothDevice : devices) {
                list.add(bluetoothDevice.getName());
            }

            list.add("취소");

            final CharSequence[] charSequences = list.toArray(new CharSequence[list.size()]);
            list.toArray(new CharSequence[list.size()]);

            builder.setItems(charSequences, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int i) {
                    connectDevice(charSequences[i].toString());
                }
            });

            builder.setCancelable(false);

            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }
    }

    //페어링 된 기기와 연결
    public void connectDevice(String deviceName) {
        String connectName = "";

        //페어링된 디바이스 탐색
        for (BluetoothDevice tempDevice : devices) {
            //사용자가 선택한 이름과 같은 디바이스로 설정
            if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getActivity(), "블루투스 사용 권한을 추가해주세요", Toast.LENGTH_SHORT).show();
                return;
            }
            if (deviceName.equals(tempDevice.getName())) {
                bluetoothDevice = tempDevice;
                connectName = tempDevice.getName();
                device.add(connectName);

                machineList = new ArrayList<>();
                machineList.add(new MachineData(R.drawable.ic_imgicon_circle, connectName, false));

                machineAdapter = new MachineAdapter(machineList, HomeFragment.this);
                machine_view.setAdapter(machineAdapter);
                machine_view.setLayoutManager(new LinearLayoutManager(getActivity()));

                if (machineList.size() == 0) {
                    machine_view.setVisibility(View.GONE);
                    nomachine_txt.setVisibility(View.VISIBLE);
                } else if (machineList.size() > 3) {
                    machine_view.setVisibility(View.VISIBLE);
                    nomachine_txt.setVisibility(View.GONE);

                    ViewGroup.LayoutParams params = machine_view.getLayoutParams();
                    params.height = 280 * machine_density;
                    machine_view.setLayoutParams(params);
                } else {
                    machine_view.setVisibility(View.VISIBLE);
                    nomachine_txt.setVisibility(View.GONE);
                }

                Toast.makeText(getActivity(), "연결 되었습니다.", Toast.LENGTH_SHORT).show();
                break;
            }
        }

        UUID uuid = java.util.UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");

        try{
            bluetoothSocket = bluetoothDevice.createRfcommSocketToServiceRecord(uuid);
            bluetoothSocket.connect();

            outputStream = bluetoothSocket.getOutputStream();
            inputStream = bluetoothSocket.getInputStream();
            receiveData();
        }catch(IOException e){
            e.printStackTrace();
        }

    }

    void sendData(String txt){
        txt += '\n';
        try {
            outputStream.write(txt.getBytes());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void receiveData() {
        final Handler handler = new Handler();
        // 데이터를 수신하기 위한 버퍼를 생성
        readBufferPosition = 0;
        readBuffer = new byte[1024];

        // 데이터를 수신하기 위한 쓰레드 생성
        workerThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while(!Thread.currentThread().isInterrupted()) {
                    try {
                        // 데이터를 수신했는지 확인합니다.
                        int byteAvailable = inputStream.available();

                        // 데이터가 수신 된 경우
                        if(byteAvailable > 0) {
                            // 입력 스트림에서 바이트 단위로 읽어 옵니다.
                            byte[] bytes = new byte[byteAvailable];
                            inputStream.read(bytes);
                            // 입력 스트림 바이트를 한 바이트씩 읽어 옵니다.
                            for(int i = 0; i < byteAvailable; i++) {
                                byte tempByte = bytes[i];
                                // 개행문자를 기준으로 받음(한줄)
                                if(tempByte == '\n') {
                                    // readBuffer 배열을 encodedBytes로 복사
                                    byte[] encodedBytes = new byte[readBufferPosition];
                                    System.arraycopy(readBuffer, 0, encodedBytes, 0, encodedBytes.length);
                                    // 인코딩 된 바이트 배열을 문자열로 변환
                                    final String text = new String(encodedBytes, "US-ASCII");
                                    readBufferPosition = 0;
                                    handler.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            // 텍스트 뷰에 출력
                                            String[] a = text.split(",");
                                            reviceTxt.setText(a[0]);
                                            try{
                                                String walkResult;
                                                ConnectWalkUpdatejActivity task = new ConnectWalkUpdatejActivity();
                                                walkResult = task.execute(userId, a[0]).get().trim();

                                                if(walkResult.equals("fail")){
                                                    Toast.makeText(getActivity(), "걸음수 업데이트 실패", Toast.LENGTH_SHORT).show();
                                                }

                                            }catch (Exception e){
                                                Log.i("DBtest", "Error");
                                            }
                                        }
                                    });
                                } // 개행 문자가 아닐 경우
                                else {
                                    readBuffer[readBufferPosition++] = tempByte;
                                }
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        // 1초마다 받아옴
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        workerThread.start();

    }


    @Override
    public void onSwitchSelected(boolean b, int position) {
        MachineAdapter.MachineHolder Holder = (MachineAdapter.MachineHolder) machine_view.findViewHolderForAdapterPosition(position);
        String name = (String) Holder.machine_nickname.getText();
        int power;
        if(b){
            sendData("o");
        }else{
            sendData("f");
        }

    }
}
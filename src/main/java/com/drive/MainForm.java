package com.drive;

import com.dropbox.core.DbxException;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.file.NotDirectoryException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by lodwr on 03.12.2015.
 */
public class MainForm extends JFrame {
    private JTextField pathField;
    private JButton startListeningButtton;
    private JPanel rootPanel;
    private JButton stopListen;
    private JTextField stats;
    private JLabel ListenDirectoryLabel;
    private JTextField threadAmmountField;
    private JTextField totalSendedFiles;
    private JTextArea errorsField;
    //    private Thread listenThread, monitorThread;
    private ExecutorService pool;
    private FileListener listener = null;
    private FileSender sender;
    private StatsService statsService;
    final DropboxConfig dbc = new DropboxConfig();

    private ExecutorService executor;


    public MainForm()
    {
        super("MyDrive");
        executor = Executors.newFixedThreadPool(2);
        pool = Executors.newFixedThreadPool(2);
        setContentPane(rootPanel);
        setSize(500, 500);
        setLocation(50,50);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);


        final DropboxConfig dbc = new DropboxConfig();
        final DropBoxSender boxSender = new DropBoxSender();


        try {
            sender = new FileSender(dbc.getClient());
        } catch (DbxException e) {
            e.printStackTrace();
        }
        statsService= new StatsService(sender);


//        startListeningButtton.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                // stopListening();
//                if(listenThread==null) {
//                    Integer.parseInt(threadAmmountField.getText());
//                    listen(pathField.getText(), Integer.parseInt(threadAmmountField.getText()));
//                    startMonitoringStats();
//                }else{
//                    final JPanel panel = new JPanel();
//                    JOptionPane.showMessageDialog(panel, "Już zostałem odpalony", "Error",
//                            JOptionPane.INFORMATION_MESSAGE);
//                }
//            }
//        });
        startListeningButtton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(validInputData()) {
                    executor.submit(() -> {
                        String threadName = Thread.currentThread().getName();
                        System.out.println("ListenThread:  " + threadName);
                        listenInThread(pathField.getText(), Integer.parseInt(threadAmmountField.getText()));
                    });
                    executor.submit(() -> {
                        String threadName = Thread.currentThread().getName();
                        System.out.println("MonitorThread:  " + threadName);
                        monitorInThread(statsService, stats, totalSendedFiles,errorsField);
                    });
                    startListeningButtton.setEnabled(false);
                }else {
                    final JPanel panel = new JPanel();
                    JOptionPane.showMessageDialog(panel, "Nieprawidłowe dane wejściowe", "Error",
                            JOptionPane.INFORMATION_MESSAGE);
                }

            }
        });
//        stopListeningButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                stopListening();
//            }
//        });

    }
    public void listenInThread(final String path, final int threadAmmount)
    {
        listener = new FileListener(path,sender,threadAmmount);
        try {
            listener.listen();
        } catch (NotDirectoryException e) {
            final JPanel panel = new JPanel();
            JOptionPane.showMessageDialog(panel, "Nieprawidlowa lokalizacja folderu", "Error",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }
    public void monitorInThread(final StatsService s, final JTextField statsField, final JTextField totalStats, final JTextArea errorsField)
    {

        int total=0;
        while(true) {
            int stats = s.getStats();
            total += stats;
            statsField.setText(String.valueOf(stats) + " files/s");
            totalStats.setText(String.valueOf(total));
            StringBuilder b = new StringBuilder();
            for(String error : s.getErrorsList())
            {
                b.append(error).append('\n');
            }
            errorsField.setText(b.toString());
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
    public boolean validInputData()
    {
        return (Validator.pointDirecory(pathField.getText())&&Validator.isNotEmptyText(threadAmmountField.getText())&&Integer.parseInt(threadAmmountField.getText())>0);
    }
//    public void stopListening()
//    {
//        listener.stopListen();
//        executor.shutdownNow();
//        startListeningButtton.setEnabled(true);
//    }


//    public void listen(final String path, final int threadAmmount)
//    {
//
//        if(threadAmmount<1){
//            final JPanel panel = new JPanel();
//            JOptionPane.showMessageDialog(panel, "Nieprawidlowa liczba wątków", "Error",
//                    JOptionPane.INFORMATION_MESSAGE);
//            return;
//        }
//        listenThread = new Thread(new Runnable() {
//            public void run() {
//                listener = new FileListener(path,sender,threadAmmount);
//                try {
//                    listener.listen();
//                } catch (NotDirectoryException e) {
//                    final JPanel panel = new JPanel();
//                    JOptionPane.showMessageDialog(panel, "Nieprawidlowa lokalizacja folderu", "Error",
//                            JOptionPane.INFORMATION_MESSAGE);
//                }
//            }
//        });
//        listenThread.start();
//    }
//    public void startMonitoringStats()
//    {
//        monitorThread = new Thread(statsUpdate(statsService,stats,totalSendedFiles));
//        monitorThread.start();
//    }
//    private Runnable statsUpdate(final StatsService s, final JTextField statsField, final JTextField totalStats){
//        return new Runnable() {
//            public void run() {
//                int total=0;
//                while(true) {
//                    int stats=s.getStats();
//                    total+=stats;
//                    statsField.setText(String.valueOf(stats)+" files/2s");
//                    totalStats.setText(String.valueOf(total));
//                    try {
//                        Thread.sleep(2000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        };
//    }

}

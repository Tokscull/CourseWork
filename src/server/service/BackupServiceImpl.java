package server.service;

import api.service.BackupService;

import java.rmi.RemoteException;
import java.rmi.server.ServerNotActiveException;
import java.rmi.server.UnicastRemoteObject;

public class BackupServiceImpl extends UnicastRemoteObject implements BackupService {
    public BackupServiceImpl() throws RemoteException {
    }


    @Override
    public String backupDb() throws RemoteException {
        try {
            System.out.print("\nClient " + getClientHost() + " request Backup.backupDb() method...");
        } catch (ServerNotActiveException e) {
            e.printStackTrace();
        }

        String massage;
        try {
            Runtime runtime = Runtime.getRuntime();
            Process p = runtime.exec("C:\\Program Files\\MySQL\\MySQL Server 8.0\\bin\\mysqldump -uroot -pegor --add-drop-database -B project -r " + "project.sql");

            int processComplete = p.waitFor();

            if (processComplete == 0) {
                massage = "Резервная копия\nуспешно созданна";
                System.out.println("[successful]");
            } else {
                massage = "Не удалось создать\nрезервную копию";
                System.out.println("[failed]");
            }

        } catch (Exception e) {
            massage = "Ошибка создания резервной копии";
            e.printStackTrace();
        }
        return massage;
    }

    @Override
    public String restoreDb() throws RemoteException {
        try {
            System.out.print("\nClient " + getClientHost() + " request Backup.restoreDb() method...");
        } catch (ServerNotActiveException e) {
            e.printStackTrace();
        }

        String massage;
        String[] restoreCmd = new String[]{"C:\\Program Files\\MySQL\\MySQL Server 8.0\\bin\\mysql ", "--user=" + "root", "--password=egor", "-e", "source " + "project.sql"};
        try {
            Process runtimeProcess = Runtime.getRuntime().exec(restoreCmd);
            int processComplete = runtimeProcess.waitFor();

            if (processComplete == 0) {
                massage = "База данных\nупешно востановлена";
                System.out.println("[successful]");
            } else {
                massage = "Резервная копия\nне найден";
                System.out.println("[failed]");
            }
        } catch (Exception ex) {
            massage = "Ошибка востановления базы данных";
            ex.printStackTrace();
        }

        return massage;
    }
}

import java.io.IOException;
import java.util.LinkedList;
import java.io.Serializable;
import javax.swing.JFileChooser;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.FileNotFoundException;
import javax.swing.filechooser.FileSystemView;


 class ReadWritePackage implements Serializable {
	private static final long serialVersionUID = 1L;
   public LinkedList<DrawItem> allComps = new LinkedList<>();
}

public class SaveFile {
	
	    public void save(ReadWritePackage pack) {
        JFileChooser op = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        op.setDialogTitle("Select location");
        op.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);

        int kp = op.showSaveDialog(null);
        if (kp == JFileChooser.APPROVE_OPTION) {
            if (!op.getCurrentDirectory().isDirectory()) {
                return;
            }
        }
        try {
            String file = op.getCurrentDirectory().getCanonicalPath() + "\\" + op.getSelectedFile().getName();
            FileOutputStream fs = new FileOutputStream(file);
            ObjectOutputStream os = new ObjectOutputStream(fs);
            os.writeObject(pack);
            os.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
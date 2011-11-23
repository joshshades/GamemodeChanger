package net.marc0303.gmchanger;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.mojang.nbt.CompressedStreamTools;
import com.mojang.nbt.NBTTagCompound;
import com.mojang.util.Util;

class Main {

	public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException
	{
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

		JFileChooser fileChooser = new JFileChooser(new File(Util.getAppDir("minecraft"), "saves"));
		fileChooser.setDialogTitle("Choose your world (level.dat)");
		fileChooser.setAcceptAllFileFilterUsed(false);
		FileFilter filter = new FileNameExtensionFilter("dat files", "dat");
		fileChooser.addChoosableFileFilter(filter);

		int ret = fileChooser.showOpenDialog(new JFrame());
		if (ret == JFileChooser.APPROVE_OPTION)
		{
			FileInputStream input = null;
			try
			{
				File file = fileChooser.getSelectedFile();
				input = new FileInputStream(file);
				NBTTagCompound in = CompressedStreamTools.readInputStreamToGzippedCompound(input);
				NBTTagCompound nbt = in.getCompoundTag("Data");
				if (nbt == null)
					showError(null);
				else
				{
					NBTTagCompound player = nbt.getCompoundTag("Player");
					NBTTagCompound abi = player.getCompoundTag("abilities");
					
					int gm;
					Object[] options = { "Survival", "Creative", "Hardcore" };

					int r = JOptionPane.showOptionDialog(null, "Choose the you want gamemode for this world", "Gamemode Changer", JOptionPane.YES_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

					gm = r;
					if (gm != 0 && gm != 1 && gm != 2)
						return;

					if(gm == 0 || gm == 2)
					{
						abi.setByte("flying", (byte) 0);
						abi.setByte("mayfly", (byte) 0);
						abi.setByte("instabuild", (byte) 0);
						abi.setByte("invulnerable", (byte) 0);
					}
					
					nbt.setInteger("GameType", gm == 2 ? 0 : gm);
					nbt.setByte("hardcore",(byte) (gm == 2 ? 1 : 0));
					in.setCompoundTag("Data", nbt);
					CompressedStreamTools.writeGzippedCompoundToOutputStream(in, new FileOutputStream(file));
					JOptionPane.showMessageDialog(null, "Done!");
					
					input.close();
				}
			} catch (IOException e)
			{
				showError(null);
			}
		}
		System.exit(0);
	}

	public static void showError(String s)
	{
		String error = "Invalid level file";
		if (s != null)
			error = s;
		JOptionPane.showMessageDialog(null, error, "Error!", JOptionPane.ERROR_MESSAGE);
	}

}

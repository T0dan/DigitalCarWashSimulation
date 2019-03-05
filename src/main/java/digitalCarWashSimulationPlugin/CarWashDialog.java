package digitalCarWashSimulationPlugin;
/*
 * 
 */


import de.neemann.digital.core.element.ElementAttributes;
import de.neemann.digital.core.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class CarWashDialog extends JDialog {
	
	private CarWashAnimation animation;
	private JPanel animationPane;
	private Timer timer;
	private EVENTS events = new EVENTS();
	private Model model;
	private CarWash carWash;
	
	private static String getDialogTitle(ElementAttributes attr, CarWash carWash) {
        String t = attr.getCleanLabel();
        if (t.length() > 0) return t;
        
        return CarWash.DESCRIPTION.getShortName();
    }
	
	/**
     * Creates a new instance
     *
     * @param parent the parent window
     * @param attr   the car wash attributes
     */
	public CarWashDialog(JFrame parent, ElementAttributes attr,Model model, CarWash carWash) {
		super(parent, getDialogTitle(attr, carWash), false);
		
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		
		this.model = model;
		this.carWash = carWash;
		getContentPane().setBackground(Color.white);
		getContentPane().setLayout(new BorderLayout());
		
		animation = new CarWashAnimation(attr.get(CarWashKeys.CARW_CARS), events, this, attr.get(CarWashKeys.CARW_CLOCK));
		
		animationPane = new JPanel(new ResizeAspectRatioLayout());
		animationPane.setBackground(Color.white);
		animationPane.add(animation);
		getContentPane().add(animationPane, BorderLayout.CENTER);
		
		JToolBar toolBar = new JToolBar();
		JButton buttonStart = new JButton(PluginLang.getTranslation("button_1"));
		JButton buttonReset = new JButton(PluginLang.getTranslation("button_2"));
		JButton buttonAddBus = new JButton(PluginLang.getTranslation("button_3"));
		JButton buttonAddPickup = new JButton(PluginLang.getTranslation("button_4"));
		JButton buttonAddCombi = new JButton(PluginLang.getTranslation("button_5"));
		JButton buttonDriveIn = new JButton(PluginLang.getTranslation("button_6"));
		buttonReset.setEnabled(false);
		
		buttonStart.addActionListener(new ActionListener(){  
			public void actionPerformed(ActionEvent e){ 
				animation.start = true;
				buttonStart.setEnabled(false);
				buttonReset.setEnabled(true);
	        }  
	    });
		buttonReset.addActionListener(new ActionListener(){  
			public void actionPerformed(ActionEvent e){ 
				animation.initCars();
				animation.reset = true;
				animation.start = false;
				buttonStart.setEnabled(true);
				buttonReset.setEnabled(false);
	        }  
	    });
		buttonAddBus.addActionListener(new ActionListener(){  
			public void actionPerformed(ActionEvent e){ 
				animation.addCar(CarType.BUS);
	        }  
	    });
		buttonAddPickup.addActionListener(new ActionListener(){  
			public void actionPerformed(ActionEvent e){ 
				animation.addCar(CarType.PICKUP);
	        }  
	    });
		buttonAddCombi.addActionListener(new ActionListener(){  
			public void actionPerformed(ActionEvent e){ 
				animation.addCar(CarType.COMBI);
	        }  
	    });
		buttonDriveIn.addActionListener(new ActionListener(){  
			public void actionPerformed(ActionEvent e){ 
				animation.letCarEnter();
	        }  
	    });
		toolBar.add(buttonStart);
		toolBar.addSeparator();
		toolBar.add(buttonReset);
		toolBar.addSeparator();
		toolBar.add(buttonAddBus);
		toolBar.addSeparator();
		toolBar.add(buttonAddPickup);
		toolBar.addSeparator();
		toolBar.add(buttonAddCombi);
		toolBar.addSeparator();
		toolBar.add(buttonDriveIn);
		getContentPane().add(toolBar, BorderLayout.NORTH);
		
		setSize(attr.get(CarWashKeys.CARW_WIDTH), attr.get(CarWashKeys.CARW_HEIGHT));
		
		setVisible(true);		
		
		
		timer = new Timer(33, new ActionListener() {
			@Override
			public void actionPerformed (ActionEvent e ) {
				animation.TimerTick();
				animation.repaint();
			}
		});
		
		timer.start();
		
		this.addWindowListener(new WindowListener() {
			@Override
			public void windowClosing(WindowEvent event) {
				timer.stop();
				event.getWindow().dispose();
			}

			@Override
			public void windowActivated(WindowEvent e) {}
			@Override
			public void windowClosed(WindowEvent e) {
				timer.stop();
			}
			@Override
			public void windowDeactivated(WindowEvent e) {}
			@Override
			public void windowDeiconified(WindowEvent e) {}
			@Override
			public void windowIconified(WindowEvent e) {}
			@Override
			public void windowOpened(WindowEvent e) {}
		});
		
	}
	
	public void update(boolean conveyorBelt, boolean trafficLight, boolean siren) {
		events.conveyerBelt = conveyorBelt;
		events.trafficLight = trafficLight;
		events.siren = siren;
	}
	
	public void updateOutput(boolean senA, boolean senB, boolean senC, boolean reset, boolean clock) {
		try {
			model.accessNEx(() -> {
				try {
					carWash.setOutputs(senA, senB, senC, reset, clock);
				} catch (NodeException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			});
		} catch (NodeException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}

class EVENTS {
	public Boolean conveyerBelt = false;
	public Boolean trafficLight = false;
	public Boolean siren = false;
}
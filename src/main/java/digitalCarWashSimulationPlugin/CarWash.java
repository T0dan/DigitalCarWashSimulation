/*
 * 
 */
package digitalCarWashSimulationPlugin;

import de.neemann.digital.core.Node;
import de.neemann.digital.core.NodeException;
import de.neemann.digital.core.ObservableValue;
import de.neemann.digital.core.ObservableValues;
import de.neemann.digital.core.element.Element;
import de.neemann.digital.core.element.ElementAttributes;
import de.neemann.digital.core.element.ElementTypeDescription;
import de.neemann.digital.core.element.Keys;
import de.neemann.digital.gui.Main;
import de.neemann.digital.gui.components.CircuitComponent;

import javax.swing.*;

import static de.neemann.digital.core.element.PinInfo.input;

/**
 */
public class CarWash extends Node implements Element {
	
	
	/**
	 * The car wash description
	 */
	public static final ElementTypeDescription DESCRIPTION = new ElementTypeDescription(CarWash.class,
			input(PluginLang.getTranslation("pin_input_1"), PluginLang.getTranslation("pin_input_1_des")),
			input(PluginLang.getTranslation("pin_input_2"), PluginLang.getTranslation("pin_input_2_des")),
			input(PluginLang.getTranslation("pin_input_3"), PluginLang.getTranslation("pin_input_3_des")))
			.addAttribute(CarWashKeys.CARW_CARS)
			.addAttribute(CarWashKeys.CARW_WIDTH)
			.addAttribute(CarWashKeys.CARW_HEIGHT)
			.addAttribute(CarWashKeys.CARW_CLOCK)
			.addAttribute(Keys.ROTATE)
			.addAttribute(Keys.LABEL)
			.setShortName(PluginLang.getTranslation("node_car_wash"));
	
	private final String label;
	private final ObservableValue sensorA;
	private final ObservableValue sensorB;
	private final ObservableValue sensorC;
	private final ObservableValue reset;
	private final ObservableValue clock;
	private boolean setClock;
    private final ElementAttributes attr;
	private CarWashDialog carWashDialog;
	
	private ObservableValue conveyorBelt;
	private ObservableValue trafficLight;
	private ObservableValue siren;
	
	/**
     * Creates a new car wash instance
     *
     * @param attributes the attributes
     */
	public CarWash(ElementAttributes attributes) {
		sensorA = new ObservableValue("SA", 1).setDescription(PluginLang.getTranslation("pin_output_1_des"));
		sensorB = new ObservableValue("SB", 1).setDescription(PluginLang.getTranslation("pin_output_2_des"));
		sensorC = new ObservableValue("SC", 1).setDescription(PluginLang.getTranslation("pin_output_3_des"));
		reset = new ObservableValue("R", 1).setDescription(PluginLang.getTranslation("pin_output_4_des"));
		clock = new ObservableValue("C", 1).setDescription(PluginLang.getTranslation("pin_output_5_des"));
		
		label = attributes.getCleanLabel();
		attr = attributes;
		setClock = attributes.get(CarWashKeys.CARW_CLOCK);
	}
	
	
	
	@Override
	public void setInputs(ObservableValues inputs) throws NodeException {
		conveyorBelt = inputs.get(0).addObserverToValue(this).checkBits(1, this);
		trafficLight = inputs.get(1).addObserverToValue(this).checkBits(1, this);
		siren = inputs.get(2).addObserverToValue(this).checkBits(1, this);
	}
	
	@Override
    public ObservableValues getOutputs() {
		if (setClock)
			return new ObservableValues(sensorA, sensorB, sensorC, reset, clock);
		else
			return new ObservableValues(sensorA, sensorB, sensorC, reset);
    }
	
	@Override
    public void readInputs() throws NodeException {
		SwingUtilities.invokeLater(() -> {
            if (carWashDialog == null || !carWashDialog.isVisible()) {
            	carWashDialog = new CarWashDialog(getModel().getWindowPosManager().getMainFrame(), attr,this.getModel(), this);
                getModel().getWindowPosManager().register("carWash_" + label, carWashDialog);
            }
            carWashDialog.update(conveyorBelt.getBool(), trafficLight.getBool(), siren.getBool());
        });
    }
	
	@Override
    public void writeOutputs() throws NodeException {
    }
	
	public void setOutputs(boolean senA, boolean senB, boolean senC, boolean reset, boolean clock) throws NodeException {
		sensorA.setBool(senA);
		sensorB.setBool(senB);
		sensorC.setBool(senC);
		this.reset.setBool(reset);
		
		if (setClock) {
			this.getModel().fireManualChangeEvent();
			this.getModel().doStep();
			this.clock.setBool(clock);
		}
		
		this.getModel().fireManualChangeEvent();
		this.getModel().doStep();
		
		Main main = ((Main) getModel().getWindowPosManager().getMainFrame());
		CircuitComponent cc = main.getCircuitComponent();
		SwingUtilities.invokeLater(() -> {
			cc.repaintNeeded();
		});
	}
}
package digitalCarWashSimulationPlugin;

import de.neemann.digital.lang.Lang;

public final class PluginLang {
	private static final String lang = Lang.currentLanguage().getName();
	
	public static String getTranslation(String key) {
		if (lang.equals("de")) {
			switch (key) {
			case "element_car_wash":
				return "Waschanlage";
			case "node_car_wash":
				return "Waschanlage";
			case "pin_input_1":
				return "F";
			case "pin_input_2":
				return "A";
			case "pin_input_3":
				return "S";
			case "pin_input_1_des":
				return "Förderband";
			case "pin_input_2_des":
				return "Ampel";
			case "pin_input_3_des":
				return "Sirene";
			case "pin_output_1":
				return "SA";
			case "pin_output_2":
				return "SB";
			case "pin_output_3":
				return "SC";
			case "pin_output_4":
				return "R";
			case "pin_output_5":
				return "C";
			case "pin_output_1_des":
				return "Sensor A";
			case "pin_output_2_des":
				return "Sensor B";
			case "pin_output_3_des":
				return "Sensor C";
			case "pin_output_4_des":
				return "Reset";
			case "pin_output_5_des":
				return "Takt";
			case "carwcars":
				return "Anzahl an Fahrzeugen";
			case "carwcars_des":
				return "Anzahl der Fahrzuege, welche während der Simulation automatisch nacheinander in die Waschanlage einfahren";
			case "carwwidth":
				return "Fensterbreite";
			case "carwwidth_des":
				return "Breite des Fensters für die Anzeige der Waschanlagen-Simulation";
			case "carwheight":
				return "Fensterhöhe";
			case "carwheight_des":
				return "Höhe des Fensters für die Anzeige der Waschanlagen-Simulation";
			case "carwclock":
				return "Taktausgang";
			case "carwclock_des":
				return "Auswahl, ob der Block einen Taktausgang besitzt, welcher bei einer Änderung geschaltet wird";
			case "button_1":
				return "Start";
			case "button_2":
				return "Reset";
			case "button_3":
				return "Bus hinzufügen";
			case "button_4":
				return "Pickup hinzufügen";
			case "button_5":
				return "Kombi hinzufügen";
			case "button_6":
				return "Fahrzeug einfahren lassen";
			case "text_1":
				return "Anzahl an Fahrzeugen in Warteschlange: ";
			}
		} else {
			switch (key) {
			case "element_car_wash":
				return "Car Wash";
			case "node_car_wash":
				return "Car Wash";
			case "pin_input_1":
				return "C";
			case "pin_input_2":
				return "T";
			case "pin_input_3":
				return "S";
			case "pin_input_1_des":
				return "conveyor belt";
			case "pin_input_2_des":
				return "traffic light";
			case "pin_input_3_des":
				return "siren";
			case "pin_output_1":
				return "SA";
			case "pin_output_2":
				return "SB";
			case "pin_output_3":
				return "SC";
			case "pin_output_4":
				return "R";
			case "pin_output_5":
				return "C";
			case "pin_output_1_des":
				return "sensor A";
			case "pin_output_2_des":
				return "sensor B";
			case "pin_output_3_des":
				return "sensor C";
			case "pin_output_4_des":
				return "reset";
			case "pin_output_5_des":
				return "clock";
			case "carwcars":
				return "count of vehicles";
			case "carwcars_des":
				return "Number of vehicles which automatically enter the car wash one after the other during the simulation";
			case "carwwidth":
				return "window width";
			case "carwwidth_des":
				return "Width of the car wash simulation screen window";
			case "carwheight":
				return "window height";
			case "carwheight_des":
				return "Height of the car wash simulation screen window";
			case "carwclock":
				return "clock output";
			case "carwclock_des":
				return "Selection of whether the block has a clock output which is switched on a change";
			case "button_1":
				return "start";
			case "button_2":
				return "reset";
			case "button_3":
				return "add bus";
			case "button_4":
				return "add pickup";
			case "button_5":
				return "add combi";
			case "button_6":
				return "let vehicle drive in";
			case "text_1":
				return "Number of vehicles in queue: ";
			}
		}
		return null;
	}
}
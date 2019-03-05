package digitalCarWashSimulationPlugin;


import java.util.*;
import java.awt.geom.*;
import java.awt.*;

public class Car {
	private CarState carState;
	private CarType carType;
	private GeneralPath carShape;
	private GeneralPath carWheel;
	
	public int posX = 0;
	public int posY = 0;
	private double angle = 0;
	public int sleepTime = 0;
	public boolean colision = false;
	
	private static final Random random = new Random();
	
	/**
     * Creates a new car instance
     */
	public Car () {
		this.carState = CarState.WAITING;
		this.carType = randomEnum(CarType.class);
		this.carShape = GenerateCar(getWidth(), getHeight(), this.carType);
		this.carWheel = AddWheel(getWidth());
		updatePosition(-10 - getWidth(), 500-getHeight(), false);
	}
	
	/**
     * Creates a new Car instance
     *
     * @param carType the type of the car
     */
	public Car (CarType carType) {
		this.carState = CarState.WAITING;
		this.carType = carType;
		this.carShape = GenerateCar(getWidth(), getHeight(), this.carType);
		this.carWheel = AddWheel(getWidth());
		updatePosition(-10 - getWidth(), 500-getHeight(), false);
	}
	
	public CarState getCarState() {
		return carState;
	}
	
	public void setCarState(CarState carState) {
		this.carState = carState;
	}
	
	public CarType getCarType() {
		return carType;
	}
	
	public Shape getCarShape() {
		return carShape;
	}
	
	public void updatePosition(int dX, int dY, boolean selfDriving) {
		this.posX += dX;
		this.posY += dY;
		AffineTransform at = new AffineTransform();
		at.setToTranslation(dX, dY);
		this.carShape.transform(at);
		if (selfDriving)
			angle += (0.03*dX);
	}
	
	public static <T extends Enum<?>> T randomEnum(Class<T> clazz){
        int x = random.nextInt(clazz.getEnumConstants().length);
        return clazz.getEnumConstants()[x];
    }
	
	public int getWidth() {
		switch (carType) {
		case COMBI:
			return 319;
		case BUS:
			return 497;
		case PICKUP:
			return 319;
		default:
			return 0;
		}
	}
	
	public int getHeight() {
		switch (carType) {
		case COMBI:
			return 161;
		case BUS:
			return 217;
		case PICKUP:
			return 219;
		default:
			return 0;
		}
	}
	
	public int getSpeed() {
		switch (carType) {
		case COMBI:
			return 8;
		case BUS:
			return 4;
		case PICKUP:
			return 6;
		default:
			return 0;
		}
	}
	
	private GeneralPath GenerateCar(double width, double height, CarType carType) {
		GeneralPath p = new GeneralPath();
		switch (carType) {
		case COMBI:
			// generate path for combi
			p.moveTo(0, 0.4*height);
			p.lineTo(0, 0.74*height);
			p.lineTo(0.13*width, 0.74*height);
			p.quadTo(0.235*width, 0.5*height, 0.34*width, 0.74*height);
			p.lineTo(0.66*width, 0.74*height);
			p.quadTo(0.755*width, 0.5*height, 0.85*width, 0.74*height);
			p.lineTo(width, 0.74*height);
			p.lineTo(width, 0.47*height);
			p.lineTo(0.74*width, 0.35*height);
			p.lineTo(0.59*width, 0);
			p.lineTo(0.075*width, 0);
			p.lineTo(0, 0.4*height);
			
			p.append(new Ellipse2D.Double(0.14*width, 0.63*height, 0.19*width, 0.19*width), false);
			p.append(new Ellipse2D.Double(0.66*width, 0.63*height, 0.19*width, 0.19*width), false);
			break;
		case BUS:
			// generate path for bus
			p.moveTo(0, 0.4*height);
			p.lineTo(0, 0.79*height);
			p.lineTo(0.14*width, 0.79*height);
			p.quadTo(0.235*width, 0.45*height, 0.33*width, 0.79*height);
			p.lineTo(0.72*width, 0.79*height);
			p.quadTo(0.815*width, 0.45*height, 0.91*width, 0.79*height);
			p.lineTo(width, 0.79*height);
			p.lineTo(width, 0.44*height);
			p.lineTo(0.85*width, 0.32*height);
			p.lineTo(0.78*width, 0);
			p.lineTo(0.04*width, 0);
			p.lineTo(0, 0.4*height);
			
			p.append(new Ellipse2D.Double(0.157*width, 0.63*height, 0.16*width, 0.16*width), false);
			p.append(new Ellipse2D.Double(0.737*width, 0.63*height, 0.16*width, 0.16*width), false);
			break;
		case PICKUP:
			// generate path for pickup
			p.moveTo(0, 0.4*height);
			p.lineTo(0, 0.78*height);
			p.lineTo(0.13*width, 0.78*height);
			p.quadTo(0.235*width, 0.6*height, 0.34*width, 0.78*height);
			p.lineTo(0.66*width, 0.78*height);
			p.quadTo(0.755*width, 0.6*height, 0.85*width, 0.78*height);
			p.lineTo(width, 0.78*height);
			p.lineTo(width, 0.6*height);
			p.lineTo(0.92*width, 0.6*height);
			p.lineTo(0.92*width, 0.4*height);
			p.lineTo(0.73*width, 0.31*height);
			p.lineTo(0.62*width, 0);
			p.lineTo(0.49*width, 0);
			p.lineTo(0.49*width, 0.4*height);
			p.lineTo(0, 0.4*height);
			
			p.append(new Ellipse2D.Double(0.14*width, 0.72*height, 0.19*width, 0.19*width), false);
			p.append(new Ellipse2D.Double(0.66*width, 0.72*height, 0.19*width, 0.19*width), false);
			break;
		default:
			break;
		}
		return p;
	}
	
	private GeneralPath AddWheel(double width) {
		GeneralPath p = new GeneralPath();
		double angle = 0;
		double radius = 0;;
		switch (carType) {
		case COMBI:
			radius = 0.095;
			break;
		case BUS:
			radius = 0.08;
			break;
		case PICKUP:
			radius = 0.095;
			break;
		}
		final int count = 7;
		for (int i=0; i<count; i++) {
			angle = Math.PI/(0.5*count)*i;
			p.moveTo(0, 0);
			p.lineTo(Math.cos(angle)*width*radius,Math.sin(angle)*width*radius);
		}
		
		return p;
	}
	
	public GeneralPath getWheels() {
		GeneralPath p = new GeneralPath();
		AffineTransform at;
		Shape s;
		switch (carType) {
		case COMBI:
			at = new AffineTransform(Math.cos(angle),Math.sin(angle),-Math.sin(angle),Math.cos(angle), (0.235*getWidth()+posX),(0.63*getHeight()+0.095*getWidth()+posY));
			s = at.createTransformedShape(carWheel);
			p.append(s, false);
			at.setToTranslation(0.52*getWidth(), 0);
			p.append(at.createTransformedShape(s), false);
			break;
		case BUS:
			at = new AffineTransform(Math.cos(angle),Math.sin(angle),-Math.sin(angle),Math.cos(angle), (0.237*getWidth()+posX),(0.63*getHeight()+0.08*getWidth()+posY));
			s = at.createTransformedShape(carWheel);
			p.append(s, false);
			at.setToTranslation(0.58*getWidth(), 0);
			p.append(at.createTransformedShape(s), false);
			break;
		case PICKUP:
			at = new AffineTransform(Math.cos(angle),Math.sin(angle),-Math.sin(angle),Math.cos(angle), (0.235*getWidth()+posX),(0.72*getHeight()+0.095*getWidth()+posY));
			s = at.createTransformedShape(carWheel);
			p.append(s, false);
			at.setToTranslation(0.52*getWidth(), 0);
			p.append(at.createTransformedShape(s), false);
			break;
		}
		return p;
	}
}

enum CarState {
	WAITING, ENTER_CAR_WASH, BEFORE_TRAFFIC_LIGHT, MANUAL_DRIVE_IN, DRIVE_IN, WASHING, WASHING_LEAVE, WASHING_END, DRIVE_OUT;
}

enum CarType {
	COMBI, BUS, PICKUP;
}
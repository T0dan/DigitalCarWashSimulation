package digitalCarWashSimulationPlugin;
/*
 * 
 */


import javax.swing.*;

import java.awt.*;
import java.awt.geom.*;
import java.util.ArrayList;
import java.util.Random;

public class CarWashAnimation extends JPanel {
	private static final int CONVEYER_SIZE = 20;
	
	public ArrayList<Car> cars = new ArrayList<Car>();
	private ArrayList<Car> carsToRemove = new ArrayList<Car>();
	private double scale;
	private double conveyerPosition;
	private double sirenPosition;
	private EVENTS events;
	private CarWashDialog cwd;
	
	private boolean sensorA;
	private boolean oldSensorA;
	private boolean sensorB;
	private boolean oldSensorB;
	private boolean sensorC;
	private boolean oldSensorC;
	
	private boolean timerTick;
	private boolean clock;
	private int clockDelay;
	
	private int carCount;
	private int carsInWait = 0;
	
	public boolean start;
	public boolean reset;
	
	private Random random = new Random();
	
	public CarWashAnimation(int carCount, EVENTS events, CarWashDialog cwd, boolean clock) {
		
		this.events = events;
		this.cwd = cwd;
		this.carCount = carCount;
		this.clock = clock;
		
		initCars();
		setDoubleBuffered(true);
		
		conveyerPosition = 0;
		sirenPosition = 0;
		
		oldSensorA = false;
		oldSensorB = false;
		oldSensorC = false;
		
		clockDelay = 0;
		start = false;
		reset = false;
		
		this.scale = 0;
		
		timerTick = false;
		setPreferredSize(new Dimension(3000, 675));
	}

	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		setBackground(new Color(255,255,255));
		super.paintComponent(g);
		
		Rectangle2D rectangle = new Rectangle2D.Double();
		RoundRectangle2D roundRectangle= new RoundRectangle2D.Double();
		Ellipse2D ellipse = new Ellipse2D.Double();
		Line2D line = new Line2D.Double();
		g2.setStroke(new BasicStroke((float)(4)));
		
		g2.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		scale = (double)getWidth()/3000.0;
		
		g2.scale(scale, scale);
		
		
		// draw sensor A
		ellipse.setFrame(580, 447, 30, 30);
		if (sensorA) {
			g2.setColor(Color.yellow);
			g2.fill(ellipse);
			g2.setColor(Color.black);
		}
		g2.draw(ellipse);
		
		// draw traffic light
		rectangle.setRect(654, 244, 29, 270);
		g2.draw(rectangle);
		roundRectangle.setRoundRect(618, 31, 99, 218, 99, 99);
		g2.setColor(getBackground());
		g2.fill(roundRectangle);
		g2.setColor(Color.black);
		g2.draw(roundRectangle);
		ellipse.setFrame(629, 57, 78, 78);
		if (!events.trafficLight) {
			g2.setColor(Color.red);
			g2.fill(ellipse);
			g2.setColor(Color.black);
		}
		g2.draw(ellipse);
		ellipse.setFrame(629, 153, 78, 78);
		if (events.trafficLight) {
			g2.setColor(Color.green);
			g2.fill(ellipse);
			g2.setColor(Color.black);
		}
		g2.draw(ellipse);
		
		// draw conveyer belt
		roundRectangle.setRoundRect(754, 504, 1005, 110, 110, 110);
		g2.draw(roundRectangle);
		ellipse.setFrame(776, 524, 65, 65);
		g2.draw(ellipse);
		ellipse.setFrame(1672, 524, 65, 65);
		g2.draw(ellipse);
		for (int i=0; i<5; i++) {
			rectangle.setRect((809-(CONVEYER_SIZE*0.5)+(i*179)+conveyerPosition), (504-CONVEYER_SIZE), CONVEYER_SIZE, CONVEYER_SIZE);
			g2.draw(rectangle);
			rectangle.setRect((1704-(CONVEYER_SIZE*0.5)-(i*179)-conveyerPosition), 614, CONVEYER_SIZE, CONVEYER_SIZE);
			g2.draw(rectangle);
		}
	
		g2.draw(RectangleOnCircle(1704, 559, CONVEYER_SIZE, CONVEYER_SIZE, 110, 110, (Math.PI/2)-(Math.PI/179)*conveyerPosition));
		g2.draw(RectangleOnCircle(809, 559, CONVEYER_SIZE, CONVEYER_SIZE, 110, 110, (Math.PI*1.5)-(Math.PI/179)*conveyerPosition));
		
		if (timerTick && start) 
			if (events.conveyerBelt)
				conveyerPosition+=4;
		if (conveyerPosition >= 179)
			conveyerPosition = 0;
		
		
		// draw sensor B
		ellipse.setFrame(1671, 447, 30, 30);
		if (sensorB) {
			g2.setColor(Color.yellow);
			g2.fill(ellipse);
			g2.setColor(Color.black);
		}
		g2.draw(ellipse);
		
		// draw entrance
		rectangle.setRect(100, 503, 630, 5);
		g2.fill(rectangle);
		g2.draw(rectangle);
		
		// draw exit
		rectangle.setRect(1789, 503, 1111, 5);
		g2.fill(rectangle);
		g2.draw(rectangle);

		// draw sensor C
		ellipse.setFrame(2389, 447, 30, 30);
		if (sensorC) {
			g2.setColor(Color.yellow);
			g2.fill(ellipse);
			g2.setColor(Color.black);
		}
		g2.draw(ellipse);
		
		// draw siren
		roundRectangle.setRoundRect(2580, -50, 86, 146, 86, 86);
		if (events.siren) {
			double sirenOffset = (-0.04)*sirenPosition*sirenPosition + 4*sirenPosition;
			g2.setColor(Color.red);
			g2.fill(roundRectangle);
			line.setLine(2569, 55, 2569-((double)34/100*sirenOffset), 55);
			g2.draw(line);
			line.setLine(2576, 82, 2576-((double)30/100*sirenOffset), 82+((double)17/100*sirenOffset));
			g2.draw(line);
			line.setLine(2596, 102, 2596-((double)17/100*sirenOffset), 102+((double)31/100*sirenOffset));
			g2.draw(line);
			line.setLine(2624, 109, 2624, 109+((double)36/100*sirenOffset));
			g2.draw(line);
			line.setLine(2650, 102, 2650+((double)17/100*sirenOffset), 102+((double)31/100*sirenOffset));
			g2.draw(line);
			line.setLine(2670, 82, 2670+((double)30/100*sirenOffset), 82+((double)17/100*sirenOffset));
			g2.draw(line);
			line.setLine(2677, 55, 2677+((double)34/100*sirenOffset), 55);
			g2.draw(line);
			g2.setColor(Color.black);
		}
		
		// draw count of cars waiting
		g2.setFont(new Font("Arial", Font.PLAIN, 55));
		g2.drawString((PluginLang.getTranslation("text_1") + carsInWait), 1000, 100);
		
		
		if (timerTick && start) 
			if (events.siren)
				sirenPosition += 8;
		if (sirenPosition >= 100)
			sirenPosition = 0;
		
		g2.draw(roundRectangle);
		rectangle.setRect(2576, 0, 94, 9);
		g2.fill(rectangle);
		
		
		for (Car car : cars) {
			if (car.getCarState() != CarState.WAITING) {
				g2.setColor(getBackground());
				if (car.colision)
					g2.setColor(Color.red);
				g2.fill(car.getCarShape());
				g2.setColor(Color.black);
				g2.draw(car.getCarShape());
				g2.draw(car.getWheels());
			}
		}
		
		timerTick = false;
	}
	
	private GeneralPath RectangleOnCircle(double centerX, double centerY, double recWidth, double recHeight, double circleWidth, double circleHeight, double angle) {
		GeneralPath p = new GeneralPath();
		double cos = Math.cos(angle);
		double sin = -Math.sin(angle);
		double phi = Math.atan2(sin, cos);
		double p1x = centerX+(cos*circleWidth*0.5);
		double p1y = centerY+(sin*circleHeight*0.5);
		p.moveTo(p1x, p1y);
		double p2x = p1x+(recWidth*0.5*Math.cos(Math.PI/2-phi));
		double p2y = p1y-(recWidth*0.5*Math.sin(Math.PI/2+phi));
		p.lineTo(p2x, p2y);
		double p3x = p2x+(recHeight*Math.cos(phi));
		double p3y = p2y+(recHeight*Math.sin(phi));
		p.lineTo(p3x, p3y);
		double p4x = p3x-(recWidth*Math.cos(Math.PI/2-phi));
		double p4y = p3y+(recWidth*Math.sin(Math.PI/2+phi));
		p.lineTo(p4x,  p4y);
		double p5x = p4x-(recHeight*Math.cos(phi));
		double p5y = p4y-(recHeight*Math.sin(phi));
		p.lineTo(p5x, p5y);
		p.lineTo(p1x, p1y);
		return p;
	}
	
	public void TimerTick() {
		sensorA = false;
		sensorB = false;
		sensorC = false;
		Boolean ready = true;
		carsInWait = 0;
		if (start) {
			for (Car car : cars) {
				if (car.getCarState() == CarState.ENTER_CAR_WASH || car.getCarState() == CarState.BEFORE_TRAFFIC_LIGHT || car.getCarState() == CarState.DRIVE_IN || car.getCarState() == CarState.MANUAL_DRIVE_IN) {
					ready = false;
				}
			}
			
			for (Car car : cars) {
				switch (car.getCarState()) {
				case WAITING:
					if (ready) {
						car.setCarState(CarState.ENTER_CAR_WASH);
						ready = false;
					}
					break;
				case ENTER_CAR_WASH:
					car.updatePosition(car.getSpeed(), 0, true);
					if (car.posX + car.getWidth() > 450)
						car.setCarState(CarState.BEFORE_TRAFFIC_LIGHT);
					break;
				case BEFORE_TRAFFIC_LIGHT:
					if (events.trafficLight) {
						car.updatePosition(car.getSpeed(), 0, true);
						if (car.posX + car.getWidth() > 568)
							car.setCarState(CarState.DRIVE_IN);
					}
					break;
				case MANUAL_DRIVE_IN:
					car.updatePosition(car.getSpeed(), 0, true);
					if (car.posX + car.getWidth() > 568)
						car.setCarState(CarState.DRIVE_IN);
					break;
				case DRIVE_IN:
					car.updatePosition(car.getSpeed(), 0, true);
					if (car.posX > 615)
						car.setCarState(CarState.WASHING);
					sensorA = true;
					break;
				case WASHING:
					if (events.conveyerBelt) {
						car.updatePosition(4, 0, false);
					}
					if (car.posX + car.getWidth() > 1660)
						car.setCarState(CarState.WASHING_LEAVE);
					break;
				case WASHING_LEAVE:
					if (events.conveyerBelt) {
						car.updatePosition(4, 0, false);
					}
					sensorB = true;
					if (car.posX > 1705) {
						car.sleepTime = random.nextInt(50);
						car.setCarState(CarState.WASHING_END);
					}
					break;
				case WASHING_END:
					if (car.sleepTime > 0) {
						car.sleepTime--;
					} else {
						car.updatePosition(car.getSpeed(), 0, true);
						if (car.posX + car.getWidth() > 2380)
							car.setCarState(CarState.DRIVE_OUT);
					}
					break;
				case DRIVE_OUT:
					car.updatePosition(car.getSpeed(), 0, true);
					if (car.posX < 2425) {
						sensorC = true;
					}
					if (car.posX > 3005)
						carsToRemove.add(car);
					break;
				default:
					break;
				}
			}
		}
		
		
		for (Car car : carsToRemove) {
			cars.remove(car);
		}
		carsToRemove.clear();
		
		for (Car car : cars) {
			if (car.getCarState() == CarState.WAITING) {
				carsInWait += 1;
			}
		}
		
		for (Car car : cars) {
			if (car.getCarState() != CarState.WAITING) {
				for (Car car2 : cars) {
					if (car2.getCarState() != CarState.WAITING && car != car2) {
						car.colision = car.getCarShape().intersects(car2.getCarShape().getBounds2D());
						car2.colision = car.colision;
						if (car.colision)
							break;
					}
				}
				if (car.colision) {
					start = false;
					break;
				}
			}
		}
		
		timerTick = true;
		if (clockDelay > 0 ) {
			clockDelay--;
			if (clockDelay == 0) {
				cwd.updateOutput(sensorA, sensorB, sensorC, false, false);
			}
		}
		
		if (oldSensorA != sensorA || oldSensorB != sensorB || oldSensorC != sensorC || reset) {
			if (clockDelay == 0) {
				clockDelay = 5;
			}
			cwd.updateOutput(sensorA, sensorB, sensorC, reset, (clockDelay > 0));
			reset = false;
			oldSensorA = sensorA;
			oldSensorB = sensorB;
			oldSensorC = sensorC;
		}
	}
	
	public void initCars() {
		cars.clear();
		for (int i=0; i<carCount; i++) {
			cars.add(new Car());
		}
	}
	
	public void addCar(CarType carType) {
		cars.add(new Car(carType));
	}
	
	public void addCar() {
		cars.add(new Car());
	}
	
	public void letCarEnter() {
		for (Car car : cars) {
			if (car.getCarState() == CarState.BEFORE_TRAFFIC_LIGHT) {
				car.setCarState(CarState.MANUAL_DRIVE_IN);
			}
		}
	}
 }
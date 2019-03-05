package digitalCarWashSimulationPlugin;

import de.neemann.digital.draw.library.ComponentManager;
import de.neemann.digital.draw.library.ComponentSource;
import de.neemann.digital.draw.library.ElementLibrary;
import de.neemann.digital.draw.library.InvalidNodeException;
import de.neemann.digital.gui.Main;

/**
 * Adds some components to Digital
 */
public class CarWashPlugin implements ComponentSource {

    /**
     * Attach your components to the simulator by calling the add methods
     *
     * @param manager the ComponentManager
     * @throws InvalidNodeException InvalidNodeException
     */
    @Override
    public void registerComponents(ComponentManager manager) throws InvalidNodeException {
    	manager.addComponent(PluginLang.getTranslation("element_car_wash"), CarWash.DESCRIPTION);
    }

    /**
     * Start Digital with this ComponentSource attached to make debugging easier.
     * IMPORTANT: Remove the jar from Digitals settings!!!
     *
     * @param args args
     */
    public static void main(String[] args) {
        new Main.MainBuilder()
                .setLibrary(new ElementLibrary().registerComponentSource(new CarWashPlugin()))
                .openLater();
    }
}

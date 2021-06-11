import common.Logger;
import common.NetRadioInstance;
import common.utils.FileUtil;
import diffuser.Diffuser;
import manager.Manager;

/**
 * Class principale qui execute soit le diffuseur soit le gestionnaire et qui
 * traites les arguments d'exécutions
 */
public class NetRadio {

	public static void main(String[] args) {
		NetRadioInstance instance = null;

		if (args.length > 0 && args[0].equalsIgnoreCase("DIFFUSER")) instance = new Diffuser();
		if (args.length > 0 && args[0].equalsIgnoreCase("MANAGER")) instance = new Manager();

		if (instance != null) {
			String[] config;
			
			if (args.length>1 && FileUtil.stringIsFile("../../" + args[1])) {
				config = new String[instance.getArguments().arguments.size()];
				FileUtil.getConfig("../../" + args[1], config);
			} else {
				config = new String[args.length-1];
				for (int i = 0; i < args.length - 1; i++) {
					config[i] = args[i + 1];
				}
			}

			String argsProblem = instance.getArguments().verify(config);

			if (argsProblem == null) instance.start(config);
			else
				Logger.log.warning(argsProblem);
		} else {
			Logger.log.warning("The first argument must be: DIFFUSER or MANAGER.");
		}
		
	}
	
}

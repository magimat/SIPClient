

import java.net.SocketException;
import java.text.SimpleDateFormat;
import java.util.Date;

import net.sourceforge.peers.Config;
import net.sourceforge.peers.Logger;
import net.sourceforge.peers.NullLogger;
import net.sourceforge.peers.sip.core.useragent.SipListener;
import net.sourceforge.peers.sip.core.useragent.UserAgent;
import net.sourceforge.peers.sip.syntaxencoding.SipHeaderFieldName;
import net.sourceforge.peers.sip.syntaxencoding.SipHeaderFieldValue;
import net.sourceforge.peers.sip.syntaxencoding.SipHeaders;
import net.sourceforge.peers.sip.syntaxencoding.SipUriSyntaxException;
import net.sourceforge.peers.sip.transport.SipRequest;
import net.sourceforge.peers.sip.transport.SipResponse;

public class EventManager implements SipListener {

    private UserAgent userAgent;
    private SipRequest sipRequest;
    private CommandsReader commandsReader;
    
    public EventManager() throws SocketException {
        Config config = new CustomConfig();
        Logger logger = new NullLogger();


        //JavaxSoundManager javaxSoundManager = new JavaxSoundManager(false, logger, null);
        userAgent = new UserAgent(this, config, logger, null); //javaxSoundManager);
        new Thread() {
            public void run() {
                try {
                    userAgent.register();
                } catch (SipUriSyntaxException e) {
                    e.printStackTrace();
                }
            }
        }.start();
        commandsReader = new CommandsReader(this);
        commandsReader.start();
    }
    
    
    // commands methods
    public void call(final String callee) {
        new Thread() {
            @Override
            public void run() {
                try {
                    sipRequest = userAgent.invite(callee, null);
                } catch (SipUriSyntaxException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
    
    public void hangup() {
        new Thread() {
            @Override
            public void run() {
                userAgent.terminate(sipRequest);
            }
        }.start();
    }
    
    
    // SipListener methods
    
    @Override
    public void registering(SipRequest sipRequest) { }

    @Override
    public void registerSuccessful(SipResponse sipResponse) { }

    @Override
    public void registerFailed(SipResponse sipResponse) { }

    @Override
    public void incomingCall(SipRequest sipRequest, SipResponse provResponse) { 
    	
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	
    	System.out.println(sdf.format(new Date()) + "   -   Appel entrant...");
    	
    	SipHeaders sh = provResponse.getSipHeaders();
    	SipHeaderFieldValue shfv = sh.get(new SipHeaderFieldName("From"));
    	
    	String s = shfv.getValue().substring(1);
    	
    	String fromName = s.substring(0, s.indexOf("\""));
    	String fromNum = s.substring(s.indexOf(":") + 1, s.indexOf("@"));

    	if(fromNum.length() == 10) {
    		fromNum = fromNum.substring(0,3) + "-" + fromNum.substring(3, 6) + "-" + fromNum.substring(6);  
    	}
    	else if(fromNum.length() == 11) {
    		fromNum = fromNum.substring(0,1) + "-" + fromNum.substring(1,4) + "-" + fromNum.substring(4, 7) + "-" + fromNum.substring(7);  
    	}
    	
    	
    	System.out.println("nom:" + fromName);
    	System.out.println("no:" + fromNum);
    	
    	
    	
    	
    	PostTV ptv = new PostTV();
    	
    	ptv.sendMsg("Appel entrant: &nbsp;&nbsp;&nbsp;" + fromName.toUpperCase() + " (" 
    				+ fromNum + ")");
    	
    	
    	
    	
    }

    @Override
    public void remoteHangup(SipRequest sipRequest) { }

    @Override
    public void ringing(SipResponse sipResponse) { }

    @Override
    public void calleePickup(SipResponse sipResponse) { }

    @Override
    public void error(SipResponse sipResponse) { }

    public static void main(String[] args) {
        try {
            new EventManager();
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }
}

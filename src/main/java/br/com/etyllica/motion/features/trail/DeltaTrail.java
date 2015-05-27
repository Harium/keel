package br.com.etyllica.motion.features.trail;

public class DeltaTrail {

  private int start = 0;

  private long startTimestamp = 0;

  private int delta = 0;

  private long deltaTimestamp = 0;
  
  private boolean hasListener = false;
  
  private TrailListener listener;

  public DeltaTrail() {
    super();
  }

  public void add(int value, long timestamp) {
    
    if(checkCommand(value, timestamp)) {
      //Evaluate command
      evaluateCommand();
      
      resetValues();
      
      //The first value after command 
      this.start = value;
      this.startTimestamp = timestamp;
    }
    
    delta += value-start;
    deltaTimestamp = timestamp-startTimestamp;
  }

  protected void resetValues() {
    delta = 0;
    deltaTimestamp = 0;
  }

  private boolean checkCommand(int value, long timestamp) {

    if (value > 0 && delta < 0) {
      return true;
    } else if (value < 0 && delta > 0) {
      return true;
    } else if (value == 0 && delta != 0) {
      return true;
    }
    return false;
  }

  private int evaluateCommand() {
    
    int position = start+delta;
    long timestamp = startTimestamp+deltaTimestamp;
    
    DeltaPoint point = new DeltaPoint(start, startTimestamp, position, timestamp);
    
    if(hasListener) {
      listener.listenTrail(point);
    }
    
    return position;
  }

  public TrailListener getListener() {
    return listener;
  }

  public void setListener(TrailListener listener) {
    this.listener = listener;
    
    //Avoid null check in the future
    if(listener != null) {
      hasListener = true;
    }
  }
  
}

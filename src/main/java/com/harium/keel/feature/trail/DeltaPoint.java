package com.harium.keel.feature.trail;

public class DeltaPoint {

  private int startPosition = 0;

  private long startTimestamp = 0;

  private int position = 0;

  private long timestamp = 0;

  public DeltaPoint(int position, long timestamp) {
    super();
    this.position = position;
    this.timestamp = timestamp;
  }

  public DeltaPoint(int startPosition, long startTimestamp, int position, long timestamp) {
    super();
    this.startPosition = startPosition;
    this.startTimestamp = startTimestamp;
    this.position = position;
    this.timestamp = timestamp;
  }



  public int getPosition() {
    return position;
  }

  public void setPosition(int position) {
    this.position = position;
  }

  public long getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(long timestamp) {
    this.timestamp = timestamp;
  }

  public int getStartPosition() {
    return startPosition;
  }

  public void setStartPosition(int startPosition) {
    this.startPosition = startPosition;
  }

  public long getStartTimestamp() {
    return startTimestamp;
  }

  public void setStartTimestamp(long startTimestamp) {
    this.startTimestamp = startTimestamp;
  }

}

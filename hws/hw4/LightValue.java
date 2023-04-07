/**
 *  LightValue enum is designed to hold place of 3 light values explained below for traffic flow.
 *  Mustafa Yigit Isik
 *  113080465
 *  CSE214_hw4
 *  Rec 02 Jamieson Barkume - Steven Secreti
 *
 *  GREEN -> indicates that the right and middle lanes may proceed, but the left lane cannot (for both directions).
 *  LEFT_SIGNAL -> indicates that left can proceed, but the right and middle lanes cannot (for both directions).
 *  RED -> indicates that no lane may proceed (for both directions).
 */

public enum LightValue {
    GREEN,
    LEFT_SIGNAL,
    RED;
}
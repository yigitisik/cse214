/**
 * CargoStrength enum is designed to hold preset category of 3 values (fragile, moderate, and sturdy).
 * Each strength level has its own carriage properties:
 * -fragile can only carry fragile
 * -moderate can carry moderate and fragile
 * -sturdy can carry all
 * Mustafa Yigit Isik
 * 113080465
 * CSE214_hw2
 * Rec 02 Jamieson Barkume - Steven Secreti
 */
public enum CargoStrength {
    FRAGILE,
    MODERATE,
    STURDY;
}
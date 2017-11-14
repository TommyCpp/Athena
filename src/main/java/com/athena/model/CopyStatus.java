package com.athena.model;

/**
 * Created by Tommy on 2017/8/25.
 */
public class CopyStatus {
    public static int CREATED = 0;
    public static int AVAILABLE = 1;
    public static int BOOKED = 2;
    public static int CHECKED_OUT = 3;
    public static int RESERVED = 4;
    public static int DAMAGED = 5;
    public static int WAIT_FOR_VERIFY = 6; // wait for admin to check if there is damage. Use when the reader return book by himself.
}

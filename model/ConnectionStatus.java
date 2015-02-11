package ch.zhaw.mas8i.pendlersupport.model;

/**
 * Enum to define the status of a connection.
 * 
 * GREEN:   There is enough time if the user walks to get the next connection
 * ORANGE:  There is enough time if the user sprints to get the next connection.
 * RED:     There is not enough time for the last connection, the next one is too far away.
 * UNKNOWN: The status is not defined yet.
 */
public enum ConnectionStatus {
	GREEN, ORANGE, RED, UNKNOWN
}
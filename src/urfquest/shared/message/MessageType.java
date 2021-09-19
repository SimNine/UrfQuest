package urfquest.shared.message;

public enum MessageType {
	PING,
	CHUNK_LOAD,
	
	// sent only by client
	PLAYER_MOVE,
	PLAYER_REQUEST,
	
	// sent only by server
	CONNECTION_CONFIRMED,
	ENTITY_INIT,
	ENTITY_SET_POS
}

package urfquest.shared.message;

public enum MessageType {
	PING,
	CHUNK_LOAD,
	
	// temporary
	DEBUG_PLAYER_INFO,
	
	// sent only by client
	PLAYER_MOVE,
	PLAYER_REQUEST,
	MAP_REQUEST,
	
	// sent only by server
	CONNECTION_CONFIRMED,
	ENTITY_INIT,
	ENTITY_SET_POS,
	MAP_METADATA
}

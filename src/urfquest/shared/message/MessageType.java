package urfquest.shared.message;

public enum MessageType {
	PING,
	CHUNK_LOAD,
	CHAT_MESSAGE,
	
	// temporary
	DEBUG_PLAYER_INFO,
	
	// sent only by client
	PLAYER_SET_MOVE_VECTOR,
	PLAYER_REQUEST,
	MAP_REQUEST,
	
	// sent only by server
	CONNECTION_CONFIRMED,
	ENTITY_INIT,
	ENTITY_SET_POS,
	ENTITY_SET_MOVE_VECTOR,
	MAP_METADATA,
	SERVER_ERROR
}

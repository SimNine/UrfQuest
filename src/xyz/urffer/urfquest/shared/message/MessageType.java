package xyz.urffer.urfquest.shared.message;

public enum MessageType {
	PING,
	CHAT_MESSAGE,
	DISCONNECT_CLIENT,
	
	// temporary
	DEBUG_PLAYER_INFO,
	
	// sent only by client
	PLAYER_SET_MOVE_VECTOR,
	PLAYER_REQUEST,
	MAP_REQUEST,
	CHUNK_REQUEST,
	
	// sent only by server
	CONNECTION_CONFIRMED,
	ENTITY_INIT,
	ENTITY_SET_POS,
	ENTITY_SET_MOVE_VECTOR,
	ENTITY_DESTROY,
	MAP_INIT,
	CHUNK_INIT,
	SERVER_ERROR
}

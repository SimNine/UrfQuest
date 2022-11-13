package xyz.urffer.urfquest.shared.protocol.types;

public enum MessageType {
	PING,
	CHAT_MESSAGE,
	DISCONNECT_CLIENT,
	
	// temporary
	DEBUG_PLAYER_INFO,
	
	// sent only by client
	PLAYER_SET_MOVE_VECTOR,
	PLAYER_REQUEST,
	REQUEST_MAP,
	REQUEST_CHUNK,
	
	// sent only by server
	CONNECTION_CONFIRMED,
	ENTITY_SET_POS,
	ENTITY_SET_MOVE_VECTOR,
	ENTITY_DESTROY,
	INIT_ENTITY,
	INIT_PLAYER,
	INIT_MAP,
	INIT_CHUNK,
	SERVER_ERROR
}

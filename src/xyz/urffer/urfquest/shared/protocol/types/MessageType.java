package xyz.urffer.urfquest.shared.protocol.types;

public enum MessageType {
	PING,
	CHAT_MESSAGE,
	DISCONNECT_CLIENT,
	MOB_SET_HELD_ITEM,
	
	// temporary
	DEBUG_PLAYER_INFO,
	
	// sent only by client
	REQUEST_PLAYER_USE_HELD_ITEM,
	REQUEST_PLAYER_SET_MOVE_VECTOR,
	REQUEST_PLAYER,
	REQUEST_MAP,
	REQUEST_CHUNK,
	
	// sent only by server
	CONNECTION_CONFIRMED,
	ENTITY_SET_POS,
	ENTITY_SET_MOVE_VECTOR,
	ENTITY_SET_DIMS,
	ENTITY_SET_STAT,
	ENTITY_DESTROY,
	INIT_MOB,
	INIT_PLAYER,
	INIT_MAP,
	INIT_CHUNK,
	INIT_ITEM,
	INIT_PROJECTILE,
	ITEM_SET_OWNER,
	ITEM_ACTIVATE_COOLDOWN,
	ITEM_RESET_COOLDOWN,
	TILE_SET,
	SERVER_ERROR
}

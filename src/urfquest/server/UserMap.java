package urfquest.server;

import java.util.HashMap;

public class UserMap {
	
	private HashMap<Integer, Integer> clientIdToPlayerId;
	private HashMap<Integer, String> clientIdToPlayerName;
	private HashMap<Integer, Integer> playerIdToClientId;
	private HashMap<Integer, String> playerIdToPlayerName;
	private HashMap<String, Integer> playerNameToClientId;
	private HashMap<String, Integer> playerNameToPlayerId;

	public UserMap() {
		this.clientIdToPlayerId = new HashMap<Integer, Integer>();
		this.clientIdToPlayerName = new HashMap<Integer, String>();
		this.playerIdToClientId = new HashMap<Integer, Integer>();
		this.playerIdToPlayerName = new HashMap<Integer, String>();
		this.playerNameToClientId = new HashMap<String, Integer>();
		this.playerNameToPlayerId = new HashMap<String, Integer>();
	}
	
	
	/*
	 * Creation of entries
	 */
	
	public void addEntry(Integer clientId, Integer playerId, String playerName) {
		clientIdToPlayerId.put(clientId, playerId);
		clientIdToPlayerName.put(clientId, playerName);
		playerIdToClientId.put(playerId, clientId);
		playerIdToPlayerName.put(playerId, playerName);
		playerNameToClientId.put(playerName, clientId);
		playerNameToPlayerId.put(playerName, playerId);
	}
	
	
	/*
	 * From clientID
	 */
	
	public Integer getPlayerIdFromClientId(Integer clientID) {
		return clientIdToPlayerId.get(clientID);
	}
	
	public String getPlayerNameFromClientId(Integer clientID) {
		return clientIdToPlayerName.get(clientID);
	}
	
	
	/*
	 * From playerID
	 */
	
	public Integer getClientIdFromPlayerId(Integer playerID) {
		return playerIdToClientId.get(playerID);
	}
	
	public String getPlayerNameFromPlayerId(Integer playerID) {
		return playerIdToPlayerName.get(playerID);
	}
	
	
	/*
	 * From playerName
	 */
	
	public Integer getClientIdFromPlayerName(String playerName) {
		return playerNameToClientId.get(playerName);
	}
	
	public Integer getPlayerIdFromPlayerName(String playerName) {
		return playerNameToPlayerId.get(playerName);
	}
	
	
	/*
	 * Removal of entries
	 */
	
	public void removeByClientId(Integer clientID) {
		Integer playerID = clientIdToPlayerId.get(clientID);
		String playerName = clientIdToPlayerName.get(clientID);
		
		this.remove(clientID, playerID, playerName);
	}
	
	public void removeByPlayerId(Integer playerID) {
		Integer clientID = playerIdToClientId.get(playerID);
		String playerName = playerIdToPlayerName.get(playerID);
		
		this.remove(clientID, playerID, playerName);
	}
	
	public void removeByPlayerName(String playerName) {
		Integer clientID = playerNameToClientId.get(playerName);
		Integer playerID = playerNameToPlayerId.get(playerName);
		
		this.remove(clientID, playerID, playerName);
	}
	
	
	/*
	 * Removal helpers 
	 */
	
	private void remove(Integer clientID, Integer playerID, String playerName) {
		clientIdToPlayerId.remove(clientID);
		clientIdToPlayerName.remove(clientID);
		playerIdToClientId.remove(playerID);
		playerIdToPlayerName.remove(playerID);
		playerNameToClientId.remove(playerName);
		playerNameToPlayerId.remove(playerName);
	}
	
}

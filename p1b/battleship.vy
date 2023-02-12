# ''' A simple implementation of battleship in Vyper '''

# NOTE: The provided code is only a suggestion
# You can change all of this code (as long as the ABI stays the same)

NUM_PIECES: constant(int128) = 5
BOARD_SIZE: constant(int128) = 5

# What phase of the game are we in ?
# Start with SET and end with END
PHASE_SET: constant(int32) = 0
PHASE_SHOOT: constant(int32) = 1
PHASE_END: constant(int32) = 2

# Each player has a 5-by-5 board
# The field track where the player's boats are located and what fields were hit
# Player should not be allowed to shoot the same field twice, even if it is empty
FIELD_EMPTY: constant(int32) = 0
FIELD_BOAT: constant(int32) = 1
FIELD_HIT: constant(int32) = 2

players: immutable(address[2])

# Which player has the next turn? Only used during the SHOOT phase
next_player: uint32

# Which phase of the game is it?
phase: int32

# my variables
board: int128[5][5][2]
#board_opp: int128[5][5][2]
pieces_1: int128 
pieces_2: int128 
hitcount_0: int128
hitcount_1: int128

@external
def __init__(player1: address, player2: address):
    players = [player1, player2]
    self.next_player = 0
    self.phase = PHASE_SET

    #TODO initialize whatever you need here
    self.board = empty(int128[5][5][2])
    #self.board_opp = empty(int128[5][5][2])
    self.pieces_1 = 0
    self.pieces_2 = 0
    self.hitcount_0 = 0
    self.hitcount_1 = 0

@external
def set_field(pos_x: int128, pos_y: int128):
    '''
    Sets a ship at the specified coordinates
    This should only be allowed in the initial phase of the game

    Players are allowed to call this out of order,
    but at most NUM_PIECES times
    '''
    if self.phase != PHASE_SET:
        raise "Wrong phase"

    if pos_x >= BOARD_SIZE or pos_y >= BOARD_SIZE:
        raise "Position out of bounds"

    #TODO add the rest here

    from_player: int128 = 0

    if msg.sender == players[0]:
        from_player = 0
        # exception where you cannot set more than 5 pieces
        if self.pieces_1 == NUM_PIECES:
            raise "Cannot set more than 5 pieces"
    elif msg.sender == players[1]:
        from_player = 1
        # exception where you cannot set more than 5 pieces
        if self.pieces_2 == NUM_PIECES:
            raise "Cannot set more than 5 pieces"
    else:
        raise "Sender is not a player"

    if self.board[from_player][pos_x][pos_y] != 0:
        raise "Field was already set"

    # player 0 setting
    if from_player == 0 and self.pieces_1 < NUM_PIECES:
        self.board[from_player][pos_x][pos_y] = 1
        self.pieces_1 = self.pieces_1 + 1
 
    # player 1 setting  
    if from_player == 1 and self.pieces_2 < NUM_PIECES:
        self.board[from_player][pos_x][pos_y] = 1
        self.pieces_2 = self.pieces_2 + 1

    # 5 pieces set by each
    if self.pieces_1 == NUM_PIECES and self.pieces_2 == NUM_PIECES:
        self.phase = PHASE_SHOOT
        #self.board_opp = self.board
        
@external
def shoot(pos_x: int128, pos_y: int128):
    '''
    Shoot a specific field on the other players board
    This should only be allowed if it is the calling player's turn and only during the SHOOT phase
    '''

    if pos_x >= BOARD_SIZE or pos_y >= BOARD_SIZE:
        raise "Position out of bounds"
    
    if self.phase != PHASE_SHOOT:
        raise "Wrong phase"

    # Add shooting logic and victory logic here

    if self.hitcount_0 == 5 or self.hitcount_1 == 5:
        raise "Cannot shoot after winning"

    from_player: uint32 = 0

    if msg.sender == players[0]:
        from_player = 0
    elif msg.sender == players[1]:
        from_player = 1
    else:
        raise "Sender is not a player"

    if from_player != self.next_player:
        raise "Not your turn!"

    if from_player == 0 and self.hitcount_0 < 5:
        # hit
        if self.board[1][pos_x][pos_y] == 1:
            self.board[1][pos_x][pos_y] = 2
            self.hitcount_0 = self.hitcount_0 + 1
            if self.hitcount_0 == 5:
                self.phase = PHASE_END
            else: 
                self.next_player = (self.next_player + 1) % 2
        # miss
        elif self.board[1][pos_x][pos_y] == 0:
            self.board[1][pos_x][pos_y] = 2
            self.next_player = (self.next_player + 1) % 2
        # retry shooting attempt
        elif self.board[1][pos_x][pos_y] == 2:
            raise "Cannot redo shoot onto same tile"

    if from_player == 1 and self.hitcount_1 < 5:
        # hit
        if self.board[0][pos_x][pos_y] == 1:
            self.board[0][pos_x][pos_y] = 2
            self.hitcount_1 = self.hitcount_1 + 1
            if self.hitcount_1 == 5:
                self.phase = PHASE_END
            else: 
                self.next_player = (self.next_player + 1) % 2
        # miss
        elif self.board[0][pos_x][pos_y] == 0:
            self.board[0][pos_x][pos_y] = 2
            self.next_player = (self.next_player + 1) % 2
        # retry shooting attempt
        elif self.board[0][pos_x][pos_y] == 2:
            raise "Cannot redo shoot onto same tile"

    #if self.hitcount_1 == 5 or self.hitcount_2==5:
    #    self.phase = PHASE_END
  

@external
@view
def has_winner() -> bool:
    return self.phase == PHASE_END

@external
@view
def get_winner() -> address:
    ''' Returns the address of the winner's account '''

    #TODO figure out who won
    if self.hitcount_0 == 5:
        return players[0]
        
    elif self.hitcount_1 == 5:
        return players[1]
        
    # Raise an error if no one won yet
    else:
        raise "No one won yet"

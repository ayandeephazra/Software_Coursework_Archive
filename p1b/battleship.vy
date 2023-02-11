# ''' A simple implementation of battleship in Vyper '''

# NOTE: The provided code is only a suggestion
# You can change all of this code (as long as the ABI stays the same)

NUM_PIECES: constant(uint32) = 5
BOARD_SIZE: constant(uint32) = 5

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
board: int32[2][5][5]
board_opp: int32[2][5][5]
pieces_1: uint32
pieces_2: uint32 
hitcount_1: uint32
hitcount_2: uint32

@external
def __init__(player1: address, player2: address):
    players = [player1, player2]
    self.next_player = 0
    self.phase = PHASE_SET

    #TODO initialize whatever you need here
    self.board = empty(int32[2][5][5])
    self.board_opp = empty(int32[2][5][5])
    self.pieces_1 = 0
    self.pieces_2 = 0
    self.hitcount_1 = 0
    self.hitcount_2 = 0

@external
def set_field(pos_x: uint32, pos_y: uint32):
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

    from_player: uint32 = 0

    if msg.sender == players[0]:
        from_player = 0
    elif msg.sender == players[1]:
        from_player = 1
    else:
        raise "Sender is not a player"

    if self.board[from_player][pos_x][pos_y] != 0:
        raise "Field was already set"

    # player 0 setting
    if from_player == 0 and self.pieces_1 < NUM_PIECES:
        self.board[from_player][pos_x][pos_y] = 1
        self.pieces_1 = self.pieces_1 + 1
    # player 1 setting  
    elif from_player == 1 and self.pieces_2 < NUM_PIECES:
        self.board[from_player][pos_x][pos_y] = 1
        self.pieces_2 = self.pieces_2 + 1
    # 5 pieces set by each
    elif self.pieces_1 == NUM_PIECES and self.pieces_2 == NUM_PIECES:
        self.phase = PHASE_SHOOT
        self.board_opp = self.board
    
@external
def shoot(pos_x: uint32, pos_y: uint32):
    '''
    Shoot a specific field on the other players board
    This should only be allowed if it is the calling player's turn and only during the SHOOT phase
    '''

    if pos_x >= BOARD_SIZE or pos_y >= BOARD_SIZE:
        raise "Position out of bounds"

    if self.phase != PHASE_SHOOT:
        raise "Wrong phase"

    # Add shooting logic and victory logic here

    from_player: uint32 = 0

    if msg.sender == players[0]:
        from_player = 0
    elif msg.sender == players[1]:
        from_player = 1
    else:
        raise "Sender is not a player"

    if from_player != self.next_player:
        raise "Not your turn!"

    if from_player == 0 and self.hitcount_1 != 5:
        # hit
        if self.board_opp[1][pos_x][pos_y] == 1:
            self.board_opp[1][pos_x][pos_y] = 2
            self.hitcount_1 = self.hitcount_1 + 1
            self.next_player = (self.next_player + 1) % 2
        # miss
        elif self.board_opp[1][pos_x][pos_y] == 0:
            self.board_opp[1][pos_x][pos_y] = 2
            self.next_player = (self.next_player + 1) % 2
        # retry shooting attempt
        elif self.board_opp[1][pos_x][pos_y] == 2:
            raise "Cannot redo shoot onto same tile"

    elif from_player == 1 and self.hitcount_2 != 5:
        # hit
        if self.board_opp[0][pos_x][pos_y] == 1:
            self.board_opp[0][pos_x][pos_y] = 2
            self.hitcount_1 = self.hitcount_1 + 1
            self.next_player = (self.next_player + 1) % 2
        # miss
        elif self.board_opp[0][pos_x][pos_y] == 0:
            self.board_opp[0][pos_x][pos_y] = 2
            self.next_player = (self.next_player + 1) % 2
        # retry shooting attempt
        elif self.board_opp[0][pos_x][pos_y] == 2:
            raise "Cannot redo shoot onto same tile"
            
    #if self.hitcount_1 == 5 or self.hitcount_2 ==5:
    #    self.has_winner()

@external
@view
def has_winner() -> bool:
    return self.phase == PHASE_END

@external
@view
def get_winner() -> address:
    ''' Returns the address of the winner's account '''

    #TODO figure out who won

    # Raise an error if no one won yet
    raise "No one won yet"

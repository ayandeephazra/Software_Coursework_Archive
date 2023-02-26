from vyper.interfaces import ERC20

implements: ERC20

event Transfer:
    sender: indexed(address)
    receiver: indexed(address)
    value: uint256

event Approval:
    owner: indexed(address)
    spender: indexed(address)
    value: uint256

balanceOf: public(HashMap[address, uint256])
allowance: public(HashMap[address, HashMap[address, uint256]])
totalSupply: public(uint256)

# TODO add state that tracks proposals here
struct proposal:
    # value won
    eth: uint256
    # who proposed it
    proposer: address
    # is the proposal approved/not 
    concluded: bool
    #voters: DynArray[address, 10]
    num_votes: uint256

# own constants
NOT_VOTED: constant(uint256) = 0
VOTED: constant(uint256) = 1

# own storage variables
yes_vote_token_count: HashMap[uint256, uint256]

# mapping from a _uid of a proposal to struct proposal,
# which has more info about the struct
proposal_list: HashMap[uint256, proposal]

# mapping from an address to a particular proposal and
# an int. int is 1 if person has voted on given 
# proposal, identified by _uid and 0 if not
voted_list: HashMap[address, HashMap[uint256, uint256]]
    
@external
def __init__():
    self.totalSupply = 0

@external
@payable
@nonreentrant("lock")
def buyToken():
    # TODO implement

    from_address: address = msg.sender
    value: uint256 = msg.value 

    # change total supply and account balance
    self.totalSupply = self.totalSupply + value
    self.balanceOf[from_address] = self.balanceOf[from_address] + value
    pass

@external
@nonpayable
@nonreentrant("lock")
def sellToken(_value: uint256):
    # TODO implement

    from_address: address = msg.sender

    # change total supply and account of person
    self.totalSupply = self.totalSupply - _value
    self.balanceOf[from_address] = self.balanceOf[from_address] - _value
    pass

# TODO add other ERC20 methods here

@external
@nonpayable
@nonreentrant("lock")
def createProposal(_uid: uint256, _recipient: address, _amount: uint256):
    # TODO implement

    if _amount == 0:
        raise "Exception: amount is 0, cannot create proposal with no reward"
 
    # condition to see if a proposal ID has been occupied or not
    if self.proposal_list[_uid].eth == 0:
        # if not, default the proposal's value field

        # default values
        self.proposal_list[_uid].eth = _amount
        # default values
        self.proposal_list[_uid].proposer = _recipient
        # has this proposal concluded?
        self.proposal_list[_uid].concluded = False
        # yes vote has how many tokens cumulatively?
        self.yes_vote_token_count[_uid] = 0
    else:
        raise "Exception: _uid already in use by another proposal"

    pass

@external
@nonpayable
@nonreentrant("lock")
def approveProposal(_uid: uint256):
    # TODO implement

    from_address: address = msg.sender
    
    # checking if no stake condition
    if self.balanceOf[from_address] == 0:
        raise "Exception: Has no stake in Token"

    # checking if certain address voted for proposal referred to by _uid
    if self.voted_list[from_address][_uid] == VOTED:
        raise "Exception: Already voted, cannot recast vote"

    log Approval(self.proposal_list[_uid].proposer, from_address, self.proposal_list[_uid].eth)
    # address's vote is counted so it is marked as VOTED
    self.voted_list[from_address][_uid] = VOTED 
    # sum of yes votes' token share
    self.yes_vote_token_count[_uid] = self.yes_vote_token_count[_uid] + self.balanceOf[from_address]

    # winning condition
    # sum of yes votes' stake > supply/2
    if self.yes_vote_token_count[_uid] > self.totalSupply/2 and self.proposal_list[_uid].concluded == False:
        send(self.proposal_list[_uid].proposer, self.proposal_list[_uid].eth)
        # so a vote can't be cast after a proposal has been decided
        self.proposal_list[_uid].concluded = True 


# ERC.vy code 
# code below borrowed in full from https://github.com/vyperlang/vyper/blob/master/examples/tokens/ERC20.vy

@external
def transfer(_to : address, _value : uint256) -> bool:
    """
    @dev Transfer token for a specified address
    @param _to The address to transfer to.
    @param _value The amount to be transferred.
    """
    # NOTE: vyper does not allow underflows
    #       so the following subtraction would revert on insufficient balance
    self.balanceOf[msg.sender] -= _value
    self.balanceOf[_to] += _value
    log Transfer(msg.sender, _to, _value)
    return True


@external
def transferFrom(_from : address, _to : address, _value : uint256) -> bool:
    """
     @dev Transfer tokens from one address to another.
     @param _from address The address which you want to send tokens from
     @param _to address The address which you want to transfer to
     @param _value uint256 the amount of tokens to be transferred
    """
    # NOTE: vyper does not allow underflows
    #       so the following subtraction would revert on insufficient balance
    self.balanceOf[_from] -= _value
    self.balanceOf[_to] += _value
    # NOTE: vyper does not allow underflows
    #      so the following subtraction would revert on insufficient allowance
    self.allowance[_from][msg.sender] -= _value
    log Transfer(_from, _to, _value)
    return True


@external
def approve(_spender : address, _value : uint256) -> bool:
    """
    @dev Approve the passed address to spend the specified amount of tokens on behalf of msg.sender.
         Beware that changing an allowance with this method brings the risk that someone may use both the old
         and the new allowance by unfortunate transaction ordering. One possible solution to mitigate this
         race condition is to first reduce the spender's allowance to 0 and set the desired value afterwards:
         https://github.com/ethereum/EIPs/issues/20#issuecomment-263524729
    @param _spender The address which will spend the funds.
    @param _value The amount of tokens to be spent.
    """
    self.allowance[msg.sender][_spender] = _value
    log Approval(msg.sender, _spender, _value)
    return True

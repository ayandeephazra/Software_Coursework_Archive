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

# own storage variables
num_stakeholders: uint256
yes_vote_token_count: uint256

# own constants
NOT_VOTED: constant(uint256) = 0
VOTED: constant(uint256) = 1

# TODO add state that tracks proposals here
struct proposal:
    eth: uint256
    proposer: address
    concluded: bool
    voters: DynArray[address, 10]
    num_votes: uint256
    
proposal_list: HashMap[uint256, proposal]
stake_list: HashMap[address, uint256]
voted_list: HashMap[address, HashMap[uint256, uint256]]

@external
def __init__():
    self.totalSupply = 0
    self.num_stakeholders = 0

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

    if self.stake_list[msg.sender] == 0:
        self.num_stakeholders = self.num_stakeholders + 1
        self.stake_list[msg.sender] = 1
    elif self.stake_list[msg.sender] == 1:
        pass
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
        raise "amount is 0, cannot create proposal with no reward"
 
    if self.proposal_list[_uid].eth == 0:
        self.proposal_list[_uid].eth = _amount
        self.proposal_list[_uid].proposer = _recipient
        self.proposal_list[_uid].num_votes = 0
        self.yes_vote_token_count = 0
        self.proposal_list[_uid].concluded = False
    else:
        raise "_uid already in use by another proposal"

    pass

@external
@nonpayable
@nonreentrant("lock")
def approveProposal(_uid: uint256):
    # TODO implement

    # checking if no stake condition
    if self.balanceOf[msg.sender] == 0:
        raise "Exception: Has no stake in Token"

    # checking if certain address voted for proposal referred to by _uid
    if self.voted_list[msg.sender][_uid] == VOTED:
        raise "Exception: Already voted, cannot recast vote"

    log Approval(self.proposal_list[_uid].proposer, msg.sender, self.proposal_list[_uid].eth)
    # determines if proposal passes or not
    self.proposal_list[_uid].num_votes += 1 
    # address's vote is counted so it is marked as VOTED
    self.voted_list[msg.sender][_uid] = VOTED 
    # sum of yes votes' token share
    self.yes_vote_token_count = self.yes_vote_token_count + self.balanceOf[msg.sender]

    # winning condition
    # sum of yes votes' stake > supply/2
    if self.yes_vote_token_count > self.totalSupply/2 and self.proposal_list[_uid].concluded == False:
        send(self.proposal_list[_uid].proposer, self.proposal_list[_uid].eth)
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

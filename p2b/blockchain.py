# forked from https://github.com/dvf/blockchain

import hashlib
import json
import time
import threading
import logging

import requests
from flask import Flask, request

class Transaction(object):
    def __init__(self, sender, recipient, amount):
        self.sender = sender # constraint: should exist in state
        self.recipient = recipient # constraint: need not exist in state. Should exist in state if transaction is applied.
        self.amount = amount # constraint: sender should have enough balance to send this amount

    def __str__(self) -> str:
        return "T(%s -> %s: %s)" % (self.sender, self.recipient, self.amount)

    def encode(self) -> str:
        return self.__dict__.copy()

    @staticmethod
    def decode(data):
        return Transaction(data['sender'], data['recipient'], data['amount'])

    def __lt__(self, other):
        if self.sender < other.sender: return True
        if self.sender > other.sender: return False
        if self.recipient < other.recipient: return True
        if self.recipient > other.recipient: return False
        if self.amount < other.amount: return True
        return False
    
    def __eq__(self, other) -> bool:
        return self.sender == other.sender and self.recipient == other.recipient and self.amount == other.amount

class Block(object):
    def __init__(self, number, transactions, previous_hash, miner):
        self.number = number # constraint: should be 1 larger than the previous block
        self.transactions = transactions # constraint: list of transactions. Ordering matters. They will be applied sequentlally.
        self.previous_hash = previous_hash # constraint: Should match the previous mined block's hash
        self.miner = miner # constraint: The node_identifier of the miner who mined this block
        self.hash = self._hash()

    def _hash(self):
        return hashlib.sha256(
            str(self.number).encode('utf-8') +
            str([str(txn) for txn in self.transactions]).encode('utf-8') +
            str(self.previous_hash).encode('utf-8') +
            str(self.miner).encode('utf-8')
        ).hexdigest()

    def __str__(self) -> str:
        return "B(#%s, %s, %s, %s, %s)" % (self.hash[:5], self.number, self.transactions, self.previous_hash, self.miner)
    
    def encode(self):
        encoded = self.__dict__.copy()
        encoded['transactions'] = [t.encode() for t in self.transactions]
        return encoded
    
    @staticmethod
    def decode(data):
        txns = [Transaction.decode(t) for t in data['transactions']]
        return Block(data['number'], txns, data['previous_hash'], data['miner'])

class State(object):
    def __init__(self):
        # TODO: You might want to think how you will store balance per person.
        # You don't need to worry about persisting to disk. Storing in memory is fine.

        # dict() to store account and balance of a person
        self.person_balance = {}
        self.person_history = {}
        pass

    # updates k-v store of person and balances
    def update_p_b(self, person, balance):
        self.person_balance.update({person: balance})

    def encode(self):
        dumped = {}
        # TODO: Add all person -> balance pairs into `dumped`.

        for person in self.person_balance:
            dumped.update({person: self.person_balance[person]})

        return dumped

    def validate_txns(self, txns):
        result = []
        # TODO: returns a list of valid transactions.
        # You receive a list of transactions, and you try applying them to the state.
        # If a transaction can be applied, add it to result. (should be included)

        for txn in txns:
            temp_txn = txn
            sender = temp_txn.sender
            receiver = temp_txn.recipient
            amount = temp_txn.amount

            if sender not in self.person_balance:
                continue

            if self.person_balance[sender] < amount:
                continue

            self.person_balance[sender] = self.person_balance - amount

            if receiver in self.person_balance:
                self.person_balance[receiver] = self.person_balance[receiver] + amount

            else:
                self.person_balance[receiver] = amount

            result.append(txn)

            #if self.person_balance[sender] >= amount:
                #self.person_balance[sender] -+ amount
                #result.append(txn)
        
        return result
    
    def apply_block(self, block):
        self.person_history.update(self.person_balance)
        for txn in block.transactions:
            sender = txn.sender
            receiver = txn.recipient
            amount = txn.amount
            self.person_balance[sender] = self.person_balance[sender] - amount
            if receiver in self.person_balance:
                self.person_balance[receiver] = self.person_balance + amount
            else:
                self.person_balance.update({receiver: amount})
        
        logging.info("Block (#%s) applied to state. %d transactions applied" % (block.hash, len(block.transactions)))


    def apply_block__(self, block):
        # TODO: apply the block to the state.

        temp_block = block # Block.decode(

        # results array
        results = self.validate_txns(temp_block.transactions)

        # iterating through valid transactions
        for txn in len(results):
            temp_txn = Transaction.decode(txn)

            # new to state initialize to 0
            if temp_txn.sender not in self.person_balance.keys():
                self.person_balance.update({temp_txn.sender: 0})

            # new to state initialize to 0
            if temp_txn.recipient not in self.person_balance.keys():
                self.person_balance.update({temp_txn.recipient: 0})

            # remove amount from sender
            self.person_balance[temp_txn.sender] = self.person_balance[temp_txn.sender] - temp_txn.amount
            # add amount to recipient 
            self.person_balance[temp_txn.recipient] = self.person_balance[temp_txn.recipient] + temp_txn.amount

            # new to state initialize to 0
            if temp_txn.sender not in self.person_history.keys():
                inner_dict_s = {}
                self.person_history.update({temp_txn.sender: inner_dict_s})

            # new to state initialize to 0
            if temp_txn.recipient not in self.person_history.keys():
                inner_dict_r = {}
                self.person_history.update({temp_txn.recipient: inner_dict_r})
            
            # history update, if account exists, then add or subtract new amt to the total
            # depending on if recipient or sender
            # else make a new entry in the list
            if temp_block.number in self.person_history[temp_txn.sender]:
                self.person_history[temp_txn.sender][temp_block.number] -= temp_txn.amount
            else :
                self.person_history[temp_txn.sender].update({temp_block.number: -1*temp_txn.amount})

            if temp_block.number in self.person_history[temp_txn.recipient]:
                self.person_history[temp_txn.recipient][temp_block.number] += temp_txn.amount
            else :
                self.person_history[temp_txn.recipient].update({temp_block.number: temp_txn.amount})

    
        logging.info("Block (#%s) applied to state. %d transactions applied" % (block.hash, len(block.transactions)))
        
    def history(self, account):
        # TODO: return a list of (blockNumber, value changes) that this account went through 
        # Here is an example

        result = []

        blockNumber = 3
        amount = 200

        blockNumber2 = 10
        amount2 = -25

        # [(blockNumber, amount), (blockNumber2, amount2)]

        num = 0
        while num < len(self.person_history) and account not in self.person_history:
            num = num + 1

        if num < len(self.person_history):
            pass

        #for k in self.person_history[account]:
        #    result.append([k,  self.person_history[account][k]])

        return result

class Blockchain(object):
    def __init__(self):
        self.nodes = []
        self.node_identifier = 0
        self.block_mine_time = 5

        # in memory datastructures.
        self.current_transactions = [] # A list of `Transaction`
        self.chain = [] # A list of `Block`
        self.state = State()
    
    def next_to_mine(self, curr_miner):
        for node in self.nodes:
            if curr_miner == node:
                return self.nodes[(self.nodes.index(node) + 1)%len(self.nodes)] # - len(self.nodes) 

    def is_new_block_valid(self, block, received_blockhash):
        """
        Determine if I should accept a new block.
        Does it pass all semantic checks? Search for "constraint" in this file.

        :param block: A new proposed block
        :return: True if valid, False if not
        """
        # TODO: check if received block is valid
        # 1. Hash should match content
        # 2. Previous hash should match previous block
        # 3. Transactions should be valid (all apply to block)
        # 4. Block number should be one higher than previous block
        # 5. miner should be correct (next RR)

        # curr block usable instance
        temp_block = Block.decode(block)
        
        if temp_block._hash() != received_blockhash:
            return False
        
        # prev block usable instance
        prev_block = Block.decode(self.chain[len(self.chain)-1])

        if temp_block.previous_hash != prev_block.hash:
            return False
        
        for txn in temp_block.transactions:
            self.current_transactions.append(txn)

        if prev_block.number != temp_block.number - 1:
            return False
        return True

    def trigger_new_block_mine(self, genesis=False):
        thread = threading.Thread(target=self.__mine_new_block_in_thread, args=(genesis,))
        thread.start()

    def __mine_new_block_in_thread(self, genesis=False):
        """
        Create a new Block in the Blockchain

        :return: New Block
        """
        logging.info("[MINER] waiting for new transactions before mining new block...")
        time.sleep(self.block_mine_time) # Wait for new transactions to come in
        miner = self.node_identifier

        if genesis:
            block = Block(1, [], '0xfeedcafe', miner)
        else:
            self.current_transactions.sort()

            # TODO: create a new *valid* block with available transactions. Replace the arguments in the line below.
            prev_block = self.chain[-1] # Block.decode(
            block = Block(len(self.chain), self.current_transactions, prev_block.hash, miner)
            # []
             
        # TODO: make changes to in-memory data structures to reflect the new block. Check Blockchain.__init__ method for in-memory datastructures
        
        if genesis:
            #pass
            # TODO: at time of genesis, change state to have 'A': 10000 (person A has 10000)
            self.state.person_balance.update({'A': 10000})
            self.state.person_history.update({'A': [[1, 10000]]})
        else:
            self.state.apply_block(block)

        self.chain.append(block)
        

        logging.info("[MINER] constructed new block with %d transactions. Informing others about: #%s" % (len(block.transactions), block.hash[:5]))
        # broadcast the new block to all nodes.
        for node in self.nodes:
            if node == self.node_identifier: continue
            requests.post(f'http://localhost:{node}/inform/block', json=block.encode())

    def new_transaction(self, sender, recipient, amount):
        """ Add this transaction to the transaction mempool. We will try
        to include this transaction in the next block until it succeeds.
        """
        self.current_transactions.append(Transaction(sender, recipient, amount))

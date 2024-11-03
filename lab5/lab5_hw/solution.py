import networkx as nx
import matplotlib.pyplot as plt

def read_input_from_file(file_path):
    transactions = dict()
    A = list()
    w = ""
    try:
        with open(file_path, 'r') as file:
            for line in file:
                line = line.strip()
                if line.startswith("("):
                    letter = None
                    left_side = None
                    right_side = set()
                    for c in line:
                        if c.isalpha():
                            if letter is None:
                                letter = c
                            elif left_side is None:
                                left_side = c
                            else:
                                right_side.add(c)

                    transactions[letter] = [left_side, right_side]
                        
                elif line.startswith("A"):
                    for c in line[1:]:
                        if c.isalpha():
                            A.append(c)
                elif line.startswith("w"):
                    for c in line[1:]:
                        if c.isalpha():
                            w += c
    except FileNotFoundError:
        print("file does not exist.")
    except IOError:
        print("I/O error occurred.")

    return transactions, A, w

def get_dependency_and_independancy_relations(transactions, A):
    D = {(x, x) for x in A}
    I = set()

    for i in range(len(A)):
        for j in range(i+1, len(A)):
            x = A[i]
            y = A[j]
            
            if transactions[x][0] in transactions[y][1] or transactions[y][0] in transactions[x][1]:
                D.add((x, y))
                D.add((y, x))
            else:
                I.add((x, y))
                I.add((y, x))

    return D, I

def get_foata_normal_form(D, w):
    foata_form = []
    current_block = set()

    for letter in w:
        if any((letter, l) in D for l in current_block):
            foata_form.append(current_block)
            current_block = {letter}
        else:
            block_to_be_added = current_block
            for block in reversed(foata_form):
                if not any((letter, l) in D for l in block):
                    block_to_be_added = block
                
            block_to_be_added.add(letter)

    if current_block:
        foata_form.append(current_block)
    
    foata_form = [sorted(block) for block in foata_form]

    return foata_form

def create_graph(foata_form, D):
    G = [[] for _ in range(len([x for y in foata_form for x in y]))]
    index = 0
    for block in foata_form:
        for i in range(len(block)):
            block[i] = (index, block[i])
            index += 1
            
    for i in range(len(foata_form)-1, -1, -1):
        for v in foata_form[i]:
            for j in range(i + 1, len(foata_form)):
                for u in foata_form[j]:
                    if (v[1], u[1]) in D:
                        if j == i + 1:
                            G[v[0]].append(u[0])
                        else:
                            if not are_connected(v[0], u[0], G):
                                G[v[0]].append(u[0])   

    return G

def are_connected(s, u, G):
    for v in G[s]:
        if v == u:
            return True
        elif are_connected(v, u, G):
            return True
    return False

def draw_graph(foata_form, D):
    vertices = [x for y in foata_form for x in y]
    label_mapping = {i: v for i, v in enumerate(vertices)}

    graph = create_graph(foata_form, D)

    G = nx.DiGraph()

    for v in label_mapping.keys():
        G.add_node(v)

    for v in range(len(graph)):
        for u in graph[v]:
            G.add_edge(v,u)

    pos = nx.shell_layout(G)
    nx.draw(G, with_labels=True, labels=label_mapping, node_size=2000, node_color='red', style='dashed', pos=pos)
    plt.show()

if __name__ == '__main__':
    transactions, A, w = read_input_from_file("case3.txt")
    D, I = get_dependency_and_independancy_relations(transactions, A)
    foata_form = get_foata_normal_form(D, w)

    D = sorted(D)
    I = sorted(I)

    print("D: ", D)
    print("I: ", I)
    print("Foata normal form: ", foata_form)

    draw_graph(foata_form, D)
    
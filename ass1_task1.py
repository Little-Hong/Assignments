import numpy as np

class Quaternion:
    
    def __init__(self,a=0,b=0,c=0,d=0):
        if (type(a) == complex) or (type(b) == complex):
            a,b,c,d = a.real,a.imag,b.real,b.imag
        if type(a) == Quaternion:
            a,b,c,d = a._q
        self._q = (a,b,c,d)
    def __getitem__(self, item):
        return self._q[(item)]

    def __str__(self):
        a, b, c, d = self.real(), self.imag(), self.jmag(), self.kmag()
        if b >= 0: b_op = '+'
        else: b_op = ''
        if c >= 0: c_op = '+'
        else: c_op = ''
        if d >= 0: d_op = '+'
        else: d_op = ''
        return "({}{}{}i{}{}j{}{}k)".format(a,b_op,b,c_op,c,d_op,d)
    def __repr__(self):
        return str(self)

    def __pos__(self):
        return self
    def __neg__(self):
        return Quaternion(-self._q[0],-self._q[1],-self._q[2],-self._q[3])
    
    def __abs__(self):
        import math
        return math.sqrt(self.real()**2+self.imag()**2+self.jmag()**2+self.kmag()**2)
    def inverse(self):
        v = abs(self) ** 2
        p = self.conjugate()
        return Quaternion(p[0]/v, p[1]/v, p[2]/v, p[3]/v)


    def __add__(self, others):
        if type(others) == int or type(others) == float:
            r = Quaternion(others,0,0,0)
        elif type(others) == complex:
            r = Quaternion(others.real,others.imag,0,0)
        elif type(others) == Quaternion:
            r = others
        return Quaternion(self.real()+r.real(),self.imag()+r.imag(),self.jmag()+r.jmag(),self.kmag()+r.kmag())
    def __radd__(self, others):
        if type(others) == int or type(others) == float:
            r = Quaternion(others,0,0,0)
        elif type(others) == complex:
            r = Quaternion(others.real,others.imag,0,0)
        elif type(others) == Quaternion:
            r = others
        return Quaternion(self.real()+r.real(),self.imag()+r.imag(),self.jmag()+r.jmag(),self.kmag()+r.kmag())
    
    def __sub__(self, others):
        if type(others) == int or type(others) == float:
            r = Quaternion(others,0,0,0)
        elif type(others) == complex:
            r = Quaternion(others.real,others.imag,0,0)
        elif type(others) == Quaternion:
            r = others
        return Quaternion(self.real()-r.real(),self.imag()-r.imag(),self.jmag()-r.jmag(),self.kmag()-r.kmag())
    def __rsub__(self, others):
        if type(others) == int or type(others) == float:
            r = Quaternion(others,0,0,0)
        elif type(others) == complex:
            r = Quaternion(others.real,others.imag,0,0)
        elif type(others) == Quaternion:
            r = others
        return Quaternion(r.real()-self.real(),r.imag()-self.imag(),r.jmag()-self.jmag(),r.kmag()-self.kmag())
    
    def __mul__(self, others):
        
        if type(others) == int or type(others) == float:
            _r = Quaternion(others,0,0,0)
        elif type(others) == complex:
            _r = Quaternion(others.real,others.imag,0,0)
        elif type(others) == Quaternion:
            _r = others
        a = self._q[0]*_r._q[0] - self._q[1]*_r._q[1] - self._q[2]*_r._q[2] - self._q[3]*_r._q[3]
        b = self._q[0]*_r._q[1] + self._q[1]*_r._q[0] + self._q[2]*_r._q[3] - self._q[3]*_r._q[2]
        c = self._q[0]*_r._q[2] + self._q[2]*_r._q[0] - self._q[1]*_r._q[3] + self._q[3]*_r._q[1]
        d = self._q[0]*_r._q[3] + self._q[3]*_r._q[0] + self._q[1]*_r._q[2] - self._q[2]*_r._q[1]
        return Quaternion(a,b,c,d)
    def __rmul__(self, others):
        if type(others) == int or type(others) == float:
            _r = Quaternion(others,0,0,0)
        elif type(others) == complex:
            _r = Quaternion(others.real,others.imag,0,0)
        elif type(others) == Quaternion:
            _r = others
        a = _r._q[0]*self._q[0] - _r._q[1]*self._q[1] - _r._q[2]*self._q[2] - _r._q[3]*self._q[3]
        b = _r._q[0]*self._q[1] + _r._q[1]*self._q[0] + _r._q[2]*self._q[3] - _r._q[3]*self._q[2]
        c = _r._q[0]*self._q[2] + _r._q[2]*self._q[0] - _r._q[1]*self._q[3] + _r._q[3]*self._q[1]
        d = _r._q[0]*self._q[3] + _r._q[3]*self._q[0] + _r._q[1]*self._q[2] - _r._q[2]*self._q[1]
        return Quaternion(a,b,c,d)
    
    def __truediv__(self, others):
        if type(others) == int or type(others) == float:
            t = Quaternion(others,0,0,0)
        elif type(others) == complex:
            t = Quaternion(others.real,others.imag,0,0)
        elif type(others) == Quaternion:
            t = others
        v = self * t.inverse()
        return Quaternion(v[0],v[1],v[2],v[3])
    def __rtruediv__(self, others):
        if type(others) == int or type(others) == float:
            t = Quaternion(others,0,0,0)
        elif type(others) == complex:
            t = Quaternion(others.real,others.imag,0,0)
        elif type(others) == Quaternion:
            t = others
        v = t * self.inverse()
        return Quaternion(v[0],v[1],v[2],v[3])

    def __pow__(self, n, modulo=None):
        if n > 0:
            if n == 1: return self
            return self * (self ** (n-1))
        if n < 0:
            if n == -1: return self.inverse()
            return self.inverse() * (self ** (n+1))
    
    def __eq__(self, others):
        if type(others) == int or type(others) == float:
            r = Quaternion(others,0,0,0)
        elif type(others) == complex:
            r = Quaternion(others.real,others.imag,0,0)
        elif type(others) == Quaternion:
            r = others
        return self._q == r._q
    
    



    def real(self): return self._q[0]
    def imag(self): return self._q[1]
    def jmag(self): return self._q[2]
    def kmag(self): return self._q[3]
    def scalar(self): return self._q[0]
    def vector(self): return (self._q[1],self._q[2],self._q[3])
    def complex_pair(self): return (self._q[0]+self._q[1]*1j, self._q[2]+self._q[3]*1j)
    def matrix(self): return np.array([self.complex_pair(),Quaternion(-self.jmag(),self.kmag(),self.real(),-self.imag()).complex_pair()],dtype=complex)
    def conjugate(self):return Quaternion(self.real(), -self.imag(), -self.jmag(), -self.kmag())



def test():
    q = Quaternion(1,2,3,4)
    r = Quaternion(5,6,7,8)

    assert q[0] == 1, "except: {}, but was: {}".format(1,q[0])
    print('pass test 1')
    assert q[2:] == (3,4), "except: {}, but was: {}".format((3,4),q[2:])
    print('pass test 2')
    assert q.real() == 1, "except: {}, but was: {}".format(1,q.real())
    print('pass test 3')
    assert q.imag() == 2, "except: {}, but was: {}".format(2,q.imag())
    print('pass test 4')
    assert q.jmag() == 3, "except: {}, but was: {}".format(3,q.jmag())
    print('pass test 5')
    assert q.kmag() == 4, "except: {}, but was: {}".format(4,q.kmag())
    print('pass test 6')
    assert q.scalar() == 1, "except: {}, but was: {}".format(1,q.scalar())
    print('pass test 7')
    assert q.vector() == (2,3,4), "except: {}, but was: {}".format((2,3,4),q.vector())
    print('pass test 8')
    assert q.complex_pair() == ((1+2j), (3+4j)), "except: {}, but was: {}".format(((1+2j), (3+4j)),q.complex_pair())
    print('pass test 9')
    assert np.any(q.matrix() == [[1.+2.j,3.+4.j],[-3.+4.j,1.-2.j]]), "except: {}, but was: {}".format([[1.+2.j,3.+4.j],[-3.+4.j,1.-2.j]],q.matrix())
    print('pass test 10')
    assert q.matrix().shape == (2,2), "except: {}, but was: {}".format((2,2), q.matrix().shape)
    print('pass test 11')
    assert type(q.matrix()) == np.ndarray, "except: {}, but was: {}".format(np.ndarray, q.matrix())
    print('pass test 12')
    assert str(q) == '(1+2i+3j+4k)', "except: {}, but was: {}".format('(1+2i+3j+4k)', str(q))
    print('pass test 13')
    assert str(+q) == '(1+2i+3j+4k)', "except: {}, but was: {}".format('(1+2i+3j+4k)', +q)
    print('pass test 14')
    assert str(-q) == '(-1-2i-3j-4k)', "except: {}, but was: {}".format('(-1-2i-3j-4k)', -q)
    print('pass test 15')
    assert str(q+r) == '(6+8i+10j+12k)', "except: {}, but was: {}".format('(6+8i+10j+12k)',q+r)
    print('pass test 16')
    assert str(q-1) == '(0+2i+3j+4k)', "except: {}, but was: {}".format('(0+2i+3j+4k)', q-1)
    print('pass test 17')
    assert str(7*q) == '(7+14i+21j+28k)', "except: {}, but was: {}".format('(7+14i+21j+28k)',7*q)
    print('pass test 18')
    assert str(30/q) == '(1.0-2.0i-3.0j-4.0k)', "except: {}, but was: {}".format('(1.0-2.0i-3.0j-4.0k)', 30/q)
    print('pass test 19')
    assert str(q-(1+2j)) == '(0.0+0.0i+3j+4k)', "except: {}, but was: {}".format('(0.0+0.0i+3j+4k)', q-(1+2j))
    print('pass tets 20')
    assert Quaternion(17,0,0,0) == 17, "except: True, but was: False"
    print('pass test 21')
    assert q != r, "except: False, but was: True"
    print('pass test 22')
    assert str(q.conjugate()) == '(1-2i-3j-4k)', "except: {}, but was: {}".format('(1-2i-3j-4k)', q.conjugate)
    print('pass test 23')
    assert str(q**3) == '(-86-52i-78j-104k)', "except: {}, but was: {}".format('(-86-52i-78j-104k)', q**3)
    print('pass test 24')
    assert abs(q) == 5.477225575051661, "except: {}, but was: {}".format(5.477225575051661, abs(q))
    print('pass test 25')
test()